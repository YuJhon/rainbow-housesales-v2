package com.jhon.rain.exception;

import com.google.common.collect.ImmutableMap;
import com.jhon.rain.common.RestCode;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.reflect.FieldUtils;

/**
 * <p>功能描述</br>通过异常获取异常码信息</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/21 20:44
 */
public class ExcpCodeUtil {

  /**
   * 注册所有的异常
   */
  private static final ImmutableMap<Object, RestCode> MAP = ImmutableMap.<Object, RestCode>builder()
          .put(NodeItemException.CustomType.WRONG_PAGE_NUM, RestCode.WRONG_PAGE)
          .put(NodeItemException.class, RestCode.UNKONWN_ERROR).build();

  /**
   * <pre>获取异常中的定义的自定义类型</pre>
   *
   * @param throwable
   * @return
   */
  private static Object getCustomType(Throwable throwable) {
    try {
      return FieldUtils.readDeclaredField(throwable, "customType", true);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * <pre>获取返回结果</pre>
   *
   * @param throwable
   * @return
   */
  public static RestCode getCode(Throwable throwable) {
    if (throwable == null) {
      return RestCode.UNKONWN_ERROR;
    }
    Object target = throwable;
    if (throwable instanceof TypeMappingException) {
      Object customType = getCustomType(throwable);
      if (customType != null) {
        target = customType;
      }
    }
    RestCode restCode = MAP.get(target);
    if (restCode != null) {
      return restCode;
    }
    Throwable rootCause = ExceptionUtils.getRootCause(throwable);
    if (rootCause != null) {
      return getCode(throwable);
    }
    return restCode.UNKONWN_ERROR;
  }
}
