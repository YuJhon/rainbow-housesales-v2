package com.jhon.rain.api.dao;

import com.jhon.rain.api.common.RestResponse;
import com.jhon.rain.api.config.GenericRest;
import com.jhon.rain.api.model.UserDO;
import com.jhon.rain.api.util.RestHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

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
      ResponseEntity<UserDO> responseEntity = rest.post(url, user,
              new ParameterizedTypeReference<UserDO>() {
              });
      return responseEntity.getBody();
    });
  }

  /**
   * <pre>激活用户</pre>
   *
   * @param key 激活码
   * @return
   */
  public boolean activateAccount(String key) {
    String result = RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(userServiceName, "/user/activate?key=" + key);
      ResponseEntity<RestResponse<String>> responseEntity = rest.get(url, new ParameterizedTypeReference<RestResponse<String>>() {
      });
      return responseEntity.getBody();
    }).getResult();
    log.info("User Activate Result = {}", result);
    return true;
  }

  /**
   * <pre>注销登录</pre>
   * @param token
   */
  public void logout(String token) {
    String result = RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(userServiceName, "/user/logout?token="+token);
      ResponseEntity<RestResponse<String>> responseEntity = rest.get(url, new ParameterizedTypeReference<RestResponse<String>>() {
      });
      return responseEntity.getBody();
    }).getResult();
    log.info("User Activate Result = {}", result);
  }
}