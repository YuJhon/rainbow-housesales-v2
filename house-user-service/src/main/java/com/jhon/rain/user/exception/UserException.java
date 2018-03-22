package com.jhon.rain.user.exception;

/**
 * <p>功能描述</br>用户模块的异常</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/22 9:48
 */
public class UserException extends RuntimeException implements TypeMappingException {

  private CustomType customType;

  public CustomType customType() {
    return customType;
  }

  public UserException(String msg) {
    super(msg);
    this.customType = CustomType.LACK_PARAMTER;
  }

  public UserException(UserException.CustomType customType, String msg) {
    super(msg);
    this.customType = customType;
  }

  public enum CustomType {
    WRONG_PAGE_NUM, LACK_PARAMTER, USER_NOT_LOGIN, USER_NOT_FOUND, USER_AUTH_FAIL;
  }
}
