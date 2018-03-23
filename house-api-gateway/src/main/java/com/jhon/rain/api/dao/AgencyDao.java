package com.jhon.rain.api.dao;

import com.jhon.rain.api.common.RestResponse;
import com.jhon.rain.api.config.GenericRest;
import com.jhon.rain.api.model.AgencyDO;
import com.jhon.rain.api.util.RestHelper;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>功能描述</br>经纪人机构的数据访问层</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/23 9:40
 */
@Repository
public class AgencyDao {

  @Autowired
  private GenericRest rest;

  @Value("${user.service.name}")
  private String userServiceName;

  /**
   * <pre>查询所有的经纪人机构</pre>
   *
   * @return
   */
  public List<AgencyDO> getAllAgency() {
    return RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(userServiceName, "/agency/list");
      ResponseEntity<RestResponse<List<AgencyDO>>> responseEntity = rest.get(url, new ParameterizedTypeReference<RestResponse<List<AgencyDO>>>() {
      });
      return responseEntity.getBody();
    }).getResult();
  }
}
