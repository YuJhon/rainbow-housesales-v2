package com.jhon.rain.api.dao;

import com.jhon.rain.api.common.RestResponse;
import com.jhon.rain.api.config.GenericRest;
import com.jhon.rain.api.model.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;

/**
 * <p>功能描述</br>测试API-Gateway项目的接口</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/20 22:38
 */
@Repository
public class UserDao {

  @Autowired
  private GenericRest rest;

  @Value("${user.service.name}")
  private String userServiceName;

  /**
   * <pre>调用服务</pre>
   *
   * @param id 用户id
   * @return 返回用户名
   */
  public String getUserNameById(Long id) {
    String url = "http://" + userServiceName + "/user/getUsername?id=" + id;
    RestResponse<String> restResponse = rest.get(url, new ParameterizedTypeReference<RestResponse<String>>() {
    }).getBody();
    if (restResponse.getCode() == 0) {
      return restResponse.getResult();
    } else {
      return null;
    }
  }
}
