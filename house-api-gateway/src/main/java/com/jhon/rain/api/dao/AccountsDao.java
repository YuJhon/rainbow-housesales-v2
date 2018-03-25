package com.jhon.rain.api.dao;

import com.jhon.rain.api.common.RestResponse;
import com.jhon.rain.api.config.GenericRest;
import com.jhon.rain.api.model.UserDO;
import com.jhon.rain.api.util.RestHelper;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.List;

/**
 * <p>功能描述</br>用户账号信息的数据访问层</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/23 9:39
 */
@Slf4j
@Repository
@DefaultProperties(groupKey = "userDao", threadPoolKey = "userDao",
        commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")},
        threadPoolProperties = {@HystrixProperty(name = "coreSize", value = "10"), @HystrixProperty(name = "maxQueueSize", value = "1000")}
)
public class AccountsDao {

  @Autowired
  private GenericRest rest;

  @Value("${user.service.name}")
  private String userServiceName;

  /**
   * <pre>获取用户列表</pre>
   *
   * @param query 用户信息
   * @return
   */
  @HystrixCommand
  public List<UserDO> getAccountList(UserDO query) {
    return RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(userServiceName, "/user/getList");
      ResponseEntity<RestResponse<List<UserDO>>> responseEntity = rest.post(url, query,
              new ParameterizedTypeReference<RestResponse<List<UserDO>>>() {
              });
      return responseEntity.getBody();
    }).getResult();
  }

  /**
   * <pre>添加用户</pre>
   *
   * @param account 注册用户信息
   * @return
   */
  @HystrixCommand
  public int addAccount(UserDO account) {
    String result = RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(userServiceName, "/user/register");
      ResponseEntity<RestResponse<String>> responseEntity = rest.post(url, account,
              new ParameterizedTypeReference<RestResponse<String>>() {
              });
      return responseEntity.getBody();
    }).getResult();
    log.info("Register Accounts Result Is {}", result);
    return 1;
  }

  /**
   * <pre>用户授权</pre>
   *
   * @param user 用户授权
   * @return
   */
  public UserDO auth(UserDO user) {
    return RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(userServiceName, "/user/auth");
      ResponseEntity<RestResponse<UserDO>> responseEntity = rest.post(url, user,
              new ParameterizedTypeReference<RestResponse<UserDO>>() {
              });
      return responseEntity.getBody();
    }).getResult();
  }

  /**
   * <pre>激活用户</pre>
   *
   * @param key 激活码
   * @return
   */
  @HystrixCommand
  public boolean activateAccount(String key) {
    String result = RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(userServiceName, "/user/activate?key=" + key);
      ResponseEntity<RestResponse<String>> responseEntity = rest.get(url,
              new ParameterizedTypeReference<RestResponse<String>>() {
              });
      return responseEntity.getBody();
    }).getResult();
    log.info("User Activate Result = {}", result);
    return true;
  }

  /**
   * <pre>注销登录</pre>
   *
   * @param token
   */
  @HystrixCommand
  public void logout(String token) {
    String result = RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(userServiceName, "/user/logout?token=" + token);
      ResponseEntity<RestResponse<String>> responseEntity = rest.get(url,
              new ParameterizedTypeReference<RestResponse<String>>() {
              });
      return responseEntity.getBody();
    }).getResult();
    log.info("User Activate Result = {}", result);
  }

  /**
   * <pre>发送重置通知</pre>
   *
   * @param email     邮箱
   * @param notifyUrl 通知地址
   */
  @HystrixCommand
  public void resetNotify(String email, String notifyUrl) {
    RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(userServiceName, "/user/resetNotify?email=" + email + "&notifyUrl=" + notifyUrl);
      ResponseEntity<RestResponse<String>> responseEntity = rest.get(url,
              new ParameterizedTypeReference<RestResponse<String>>() {
              });
      return responseEntity.getBody();
    });
  }

  /**
   * <pre>获取重置的邮箱</pre>
   *
   * @param key
   * @return
   */
  @HystrixCommand
  public String getResetEmail(String key) {
    return RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(userServiceName, "/user/getEmailByKey?key=" + key);
      ResponseEntity<RestResponse<String>> responseEntity = rest.get(url,
              new ParameterizedTypeReference<RestResponse<String>>() {
              });
      return responseEntity.getBody();
    }).getResult();
  }

  /**
   * <pre>重置密码</pre>
   *
   * @param key    获取邮件的key
   * @param passwd 重置之后的密码
   * @return
   */
  @HystrixCommand
  public UserDO resetPassword(String key, String passwd) {
    return RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(userServiceName, "/user/resetPwd?key=" + key + "&password=" + passwd);
      ResponseEntity<RestResponse<UserDO>> responseEntity = rest.get(url,
              new ParameterizedTypeReference<RestResponse<UserDO>>() {
              });
      return responseEntity.getBody();
    }).getResult();
  }

  /**
   * <pre>更新用户信息</pre>
   *
   * @param user
   * @return
   */
  @HystrixCommand
  public UserDO updateUserInfo(UserDO user) {
    return RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(userServiceName, "/user/updateInfo");
      ResponseEntity<RestResponse<UserDO>> responseEntity = rest.post(url, user,
              new ParameterizedTypeReference<RestResponse<UserDO>>() {
              });
      return responseEntity.getBody();
    }).getResult();
  }

  /**
   * <pre>通过token查询账号信息</pre>
   *
   * @param token
   * @return
   */
  /*(fallbackMethod = "queryAccountByTokenFB")*/
  @HystrixCommand(fallbackMethod = "queryAccountByTokenFB")
  public UserDO queryAccountByToken(String token) {
    /*return RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(userServiceName, "/user/getUserByToken?token=" + token);
      ResponseEntity<RestResponse<UserDO>> responseEntity = rest.get(url,
              new ParameterizedTypeReference<RestResponse<UserDO>>() {
              });
      return responseEntity.getBody();
    }).getResult();*/
    String url = RestHelper.formatUrl(userServiceName, "/user/getUserByToken?token=" + token);
    ResponseEntity<RestResponse<UserDO>> responseEntity = rest.get(url, new ParameterizedTypeReference<RestResponse<UserDO>>() {});
    RestResponse<UserDO> response = responseEntity.getBody();
    if (response == null || response.getCode() != 0) {
      return null;
    }
    return response.getResult();
  }

  /**
   * <pre>降级方法</pre>
   *
   * @param token
   * @return
   */
  public UserDO queryAccountByTokenFB(String token) {
    return new UserDO();
  }
}
