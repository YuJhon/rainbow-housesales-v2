package com.jhon.rain.api.service;

import com.jhon.rain.api.model.UserDO;

import java.util.List;

/**
 * <p>功能描述</br>账号体系的业务逻辑接口</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/23 9:38
 */
public interface AccountsService {
  /**
   * <pre>添加用户</pre>
   *
   * @param account 用户
   * @return 返回影响的记录数
   */
  int addAccount(UserDO account);

  /**
   * <pre>判断用户是否存在</pre>
   *
   * @param email 用户邮箱
   * @return
   */
  boolean isExist(String email);

  /**
   * <pre>激活用户</pre>
   *
   * @param key 激活key
   * @return
   */
  boolean enable(String key);

  /**
   * <pre>用户的认证</pre>
   *
   * @param email    邮箱
   * @param password 密码
   * @return 用户信息
   */
  UserDO auth(String email, String password);

  /**
   * <pre>密码重置</pre>
   *
   * @param key 从缓存中获取key对应的邮箱
   * @return 返回用户邮箱标识
   */
  String getResetEmail(String key);

  /**
   * <pre>重置用户密码</pre>
   *
   * @param key    邮件缓存key
   * @param passwd 密码
   * @return 更新后的用户信息
   */
  UserDO reset(String key, String passwd);

  /**
   * <pre>重置密码通知邮件</pre>
   *
   * @param email
   */
  void resetNotify(String email);

  /**
   * <pre>更新用户信息</pre>
   *
   * @param user
   * @return
   */
  UserDO updateUser(UserDO user);

  /**
   * <pre>查询用户信息</pre>
   *
   * @param user
   * @return
   */
  List<UserDO> getUserByQuery(UserDO user);

  /**
   * <pre>注销登录</pre>
   *
   * @param token
   */
  void logout(String token);

  /**
   * <pre>重置</pre>
   *
   * @param email
   */
  void resetEmailNotify(String email);
}
