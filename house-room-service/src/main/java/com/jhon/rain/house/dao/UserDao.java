package com.jhon.rain.house.dao;

import com.jhon.rain.house.common.RestResponse;
import com.jhon.rain.house.config.GenericRest;
import com.jhon.rain.house.model.UserDO;
import com.jhon.rain.house.utils.RestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 14:19
 */
@Repository
public class UserDao {

  @Autowired
  private GenericRest rest;

  @Value("${user.service.name}")
  private String userServiceName;

  /**
   * <pre>获取用户信息</pre>
   *
   * @param agentId
   * @return
   */
  public UserDO getAgentDetail(Long agentId) {
    return RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(userServiceName, "/agency/agentDetail?id=" + agentId);
      ResponseEntity<RestResponse<UserDO>> responseEntity = rest.get(url,
              new ParameterizedTypeReference<RestResponse<UserDO>>() {
              });
      return responseEntity.getBody();
    }).getResult();
  }
}
