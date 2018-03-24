package com.jhon.rain.comment.dao;

import com.jhon.rain.comment.common.RestResponse;
import com.jhon.rain.comment.config.GenericRest;
import com.jhon.rain.comment.model.UserDO;
import com.jhon.rain.comment.utils.RestHelper;
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
 * @date 2018/3/24 19:53
 */
@Repository
public class UserDao {

  @Autowired
  private GenericRest rest;

  @Value("${user.service.name}")
  private String userServiceName;

  /**
   * <pre>查询用户信息</pre>
   *
   * @param userId 用户Id
   * @return
   */
  public UserDO getUserDetailById(Long userId) {
    return RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(userServiceName, "/user/getById?id=" + userId);
      ResponseEntity<RestResponse<UserDO>> responseEntity = rest.get(url,
              new ParameterizedTypeReference<RestResponse<UserDO>>() {
              });
      return responseEntity.getBody();
    }).getResult();
  }
}
