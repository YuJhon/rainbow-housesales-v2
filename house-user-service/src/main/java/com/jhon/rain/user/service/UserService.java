package com.jhon.rain.user.service;

import com.jhon.rain.user.model.UserDO;

import java.util.List;

/**
 * <p>功能描述</br>用户业务逻辑接口定义</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/21 21:49
 */
public interface UserService {
  /**
   * <pre>通过id查询用户</pre>
   *
   * @param id 主键Id
   * @return
   */
  UserDO getUserById(Long id);

  /**
   * <pre>查询用户列表</pre>
   *
   * @param user
   * @return
   */
  List<UserDO> getUserList(UserDO user);

  /**
   * <pre>注册新用户</pre>
   *
   * @param account
   * @param enableUrl 激活链接
   * @return
   */
  int registerAccount(UserDO account, String enableUrl);

  /**
   * <pre>激活用户</pre>
   *
   * @param key
   * @return
   */
  int activateAccount(String key);

  /**
   * <pre>用户鉴权</pre>
   *
   * @param email    邮箱
   * @param password 密码
   * @return
   */
  UserDO auth(String email, String password);

  /**
   * <pre>通过Token换取登录的用户</pre>
   *
   * @param token token信息
   * @return
   */
  UserDO getLoginedUserByToken(String token);

  /**
   * <pre>失效token</pre>
   *
   * @param token token信息
   */
  void invalidate(String token);

  /**
   * <pre>更新用户信息</pre>
   *
   * @param userInfo
   * @return
   */
  UserDO updateUserInfo(UserDO userInfo);

  /**
   * <pre>重置密码</pre>
   *
   * @param key      重置密码的key
   * @param password 新密码
   * @return
   */
  UserDO resetPassword(String key, String password);

  /**
   * <pre>通过key查询邮箱信息</pre>
   *
   * @param key
   * @return
   */
  String getEmailByKey(String key);

  /**
   * <pre>重置密码邮箱通知</pre>
   *
   * @param email     用户邮箱
   * @param notifyUrl 通知的url
   */
  void resetEmailNotify(String email, String notifyUrl);
}
