package com.jhon.rain.api.interceptor;


import com.jhon.rain.api.model.UserDO;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/23 20:07
 */
public class UserContext {

  /**
   * 申明线程局部变量，用于存放当前操作的用户
   **/
  public static final ThreadLocal<UserDO> USER_HOLDER = new ThreadLocal<>();

  public static void setUser(UserDO user) {
    USER_HOLDER.set(user);
  }

  public static void remove() {
    USER_HOLDER.remove();
  }

  public static UserDO getUser() {
    return USER_HOLDER.get();
  }
}
