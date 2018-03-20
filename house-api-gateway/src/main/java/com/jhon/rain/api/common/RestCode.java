package com.jhon.rain.api.common;

/**
 * <p>功能描述</br>返回码</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/20 21:59
 */
public enum RestCode {
  OK(0, "OK"),
  UNKONWN_ERROR(1, "用户服务异常"),
  WRONG_PAGE(2, "页码不合法"),
  USER_NOT_FOUND(3, "用户未找到"),
  ILLEGAL_PARAMS(4, "参数不合法");

  public final int code;
  public final String msg;

  private RestCode(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }
}
