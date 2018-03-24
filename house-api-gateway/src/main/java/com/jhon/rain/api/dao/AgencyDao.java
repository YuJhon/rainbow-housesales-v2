package com.jhon.rain.api.dao;

import com.jhon.rain.api.common.RestResponse;
import com.jhon.rain.api.config.GenericRest;
import com.jhon.rain.api.model.AgencyDO;
import com.jhon.rain.api.model.HouseDO;
import com.jhon.rain.api.model.UserDO;
import com.jhon.rain.api.model.dto.PageListResponse;
import com.jhon.rain.api.util.RestHelper;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.List;

/**
 * <p>功能描述</br>经纪人机构的数据访问层</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/23 9:40
 */
@Slf4j
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
      ResponseEntity<RestResponse<List<AgencyDO>>> responseEntity = rest.get(url,
              new ParameterizedTypeReference<RestResponse<List<AgencyDO>>>() {
              });
      return responseEntity.getBody();
    }).getResult();
  }

  /**
   * <pre>查询经纪机构人的详细信息</pre>
   *
   * @param userId 用户Id
   * @return
   */
  public UserDO getAgentDetail(Long userId) {
    return RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(userServiceName, "/agency/agentDetail?id=" + userId);
      ResponseEntity<RestResponse<UserDO>> responseEntity = rest.get(url,
              new ParameterizedTypeReference<RestResponse<UserDO>>() {
              });
      return responseEntity.getBody();
    }).getResult();
  }

  /**
   * <pre>添加经纪人机构</pre>
   *
   * @param agency
   * @return
   */
  public int add(AgencyDO agency) {
    String result = RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(userServiceName, "/agency/add");
      ResponseEntity<RestResponse<String>> responseEntity = rest.post(url, agency,
              new ParameterizedTypeReference<RestResponse<String>>() {
              });
      return responseEntity.getBody();
    }).getResult();
    log.info("Rest Call Add Agency Result = {}", result);
    return 1;
  }

  /**
   * <pre>分页查询经纪人</pre>
   *
   * @param pageNum  当前页
   * @param pageSize 每页大小
   * @return
   */
  public PageListResponse<UserDO> queryAgent(Integer pageNum, Integer pageSize) {
    return RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(userServiceName, "/agency/agentList?pageNum=" + pageNum + "&pageSize=" + pageSize);
      ResponseEntity<RestResponse<PageListResponse<UserDO>>> responseEntity = rest.get(url,
              new ParameterizedTypeReference<RestResponse<PageListResponse<UserDO>>>() {
              });
      return responseEntity.getBody();
    }).getResult();
  }
}
