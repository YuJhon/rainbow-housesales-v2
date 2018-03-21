package com.jhon.rain.exception;

import com.jhon.rain.common.RestCode;
import com.jhon.rain.common.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>功能描述</br>定义全局的异常类</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/21 20:30
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  /**
   * <pre>捕获所有的异常信息</pre>
   *
   * @param request   请求对象
   * @param throwable 异常
   * @return 统一的返回结果
   */
  @ResponseStatus(HttpStatus.OK)
  @ExceptionHandler(value = Throwable.class)
  public RestResponse<Object> handler(HttpServletRequest request, Throwable throwable) {
    log.error(throwable.getMessage(), throwable);
    RestCode restCode = ExcpCodeUtil.getCode(throwable);
    RestResponse<Object> response = new RestResponse<Object>(restCode.code, restCode.msg);
    return response;
  }
}
