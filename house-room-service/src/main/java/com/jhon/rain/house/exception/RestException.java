package com.jhon.rain.house.exception;

/**
 * <p>功能描述</br>Rest远程调用异常定义</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/23 14:38
 */
public class RestException extends RuntimeException {

  public RestException(String errorCode) {
    super(errorCode);
  }

  public RestException(String errorCode, Throwable cause) {
    super(errorCode, cause);
  }

  public RestException(String errorCode, String errorMsg) {
    super(errorCode + ":" + errorMsg);
  }

  public RestException(String errorCode, String errorMsg, Throwable cause) {
    super(errorCode + ":" + errorMsg, cause);
  }

  public RestException(Throwable cause) {
    super(cause);
  }
}
