package com.jhon.rain.api.util;

import com.jhon.rain.api.exception.RestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.reflect.FieldUtils;
import sun.reflect.misc.FieldUtil;

import java.util.concurrent.Callable;

/**
 * <p>功能描述</br>Rest工具的二次封装</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/23 14:17
 */
@Slf4j
public final class RestHelper {

  private RestHelper() {
  }

  private static DefaultHandler defaultHandler = new DefaultHandler();

  public static <T> T exec(Callable<T> callable) {
    return exec(callable, defaultHandler);
  }

  /**
   * <pre></pre>
   * @param callable
   * @param handler
   * @param <T>
   * @return
   */
  public static <T> T exec(Callable<T> callable, ResultHandler handler) {
    T result = processReq(callable);
    return handler.handle(result);
  }

  /**
   * <pre>组织url</pre>
   *
   * @param serviceName 服务名称
   * @param path        后面的路径
   * @return 构件好的url
   */
  public static String formatUrl(String serviceName, String path) {
    return new StringBuilder("http://").append(serviceName).append(path).toString();
  }

  /**
   * 默认的处理器
   */
  public static class DefaultHandler implements ResultHandler {

    @Override
    public <T> T handle(T result) {
      int code = 1;
      String msg = "";
      try {
        code = (Integer) FieldUtils.readDeclaredField(result, "code", true);
        msg = (String) FieldUtils.readDeclaredField(result, "msg", true);
      } catch (Exception e) {
        // ignore
      }
      if (code != 0) {
        log.error("Remote Rest Call Service Request Exception: ErrorCode={},ErrorMsg={}", code, msg);
        throw new RestException("Remote Rest Call Service Request Exception: ErrorCode={" + code + "},ErrorMsg={" + msg + "}");
      }
      return result;
    }
  }

  /**
   * 请求的处理
   *
   * @param callable 异步接口
   * @param <T>
   * @return
   */
  public static <T> T processReq(Callable<T> callable) {
    T result = null;
    try {
      result = callable.call();
    } catch (Exception e) {
      throw new RestException("Remote Rest Call Service Request Exception: ErrorMsg={" + e.getMessage() + "}");
    } finally {
      log.info("result={}", result);
    }
    return result;
  }

  /**
   * 结果的处理
   */
  public interface ResultHandler {
    <T> T handle(T result);
  }
}
