package com.jhon.rain.api.dao;

import com.jhon.rain.api.HouseUserTypeEnum;
import com.jhon.rain.api.common.RestResponse;
import com.jhon.rain.api.config.GenericRest;
import com.jhon.rain.api.model.CityDO;
import com.jhon.rain.api.model.CommunityDO;
import com.jhon.rain.api.model.HouseDO;
import com.jhon.rain.api.model.HouseMsgDO;
import com.jhon.rain.api.model.dto.PageListResponse;
import com.jhon.rain.api.model.vo.HouseQueryReq;
import com.jhon.rain.api.model.vo.HouseUserReq;
import com.jhon.rain.api.util.RestHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 14:54
 */
@Slf4j
@Repository
public class HouseDao {

  @Autowired
  private GenericRest rest;

  @Value("${house.service.name}")
  private String houseServiceName;

  /**
   * <pre>查询最热门的房产</pre>
   *
   * @param size
   * @return
   */
  public List<HouseDO> queryHotHouses(int size) {
    return RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(houseServiceName, "/house/hot?size=" + size);
      ResponseEntity<RestResponse<List<HouseDO>>> responseEntity = rest.get(url,
              new ParameterizedTypeReference<RestResponse<List<HouseDO>>>() {
              });
      return responseEntity.getBody();
    }).getResult();
  }

  /**
   * <pre>获取最新房产</pre>
   *
   * @param size
   * @return
   */
  public List<HouseDO> queryLatestHouses(int size) {
    return RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(houseServiceName, "/house/latest?size=" + size);
      ResponseEntity<RestResponse<List<HouseDO>>> responseEntity = rest.get(url,
              new ParameterizedTypeReference<RestResponse<List<HouseDO>>>() {
              });
      return responseEntity.getBody();
    }).getResult();
  }

  /**
   * <pre>通过房产Id查询房产信息</pre>
   *
   * @param houseId
   * @return
   */
  public HouseDO queryHouseById(Long houseId) {
    return RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(houseServiceName, "/house/detail?id=" + houseId);
      ResponseEntity<RestResponse<HouseDO>> responseEntity = rest.get(url,
              new ParameterizedTypeReference<RestResponse<HouseDO>>() {
              });
      return responseEntity.getBody();
    }).getResult();
  }

  /**
   * <pre>添加房产消息</pre>
   *
   * @param houseMsg
   * @return
   */
  public int addHouseMsg(HouseMsgDO houseMsg) {
    String result = RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(houseServiceName, "/house/addMsg");
      ResponseEntity<RestResponse<String>> responseEntity = rest.post(url, houseMsg,
              new ParameterizedTypeReference<RestResponse<String>>() {
              });
      return responseEntity.getBody();
    }).getResult();
    log.info("Rest Call Add HouseMsg Result={}", result);
    return 1;
  }

  /**
   * <pre>更新房产的等级</pre>
   *
   * @param id     房产Id
   * @param rating 等级
   * @return
   */
  public int updateRating(Long id, Double rating) {
    String result = RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(houseServiceName, "/house/detail?id=" + id + "&rating=" + rating);
      ResponseEntity<RestResponse<String>> responseEntity = rest.get(url,
              new ParameterizedTypeReference<RestResponse<String>>() {
              });
      return responseEntity.getBody();
    }).getResult();
    log.info("Rest Call Add updateRating Result={}", result);
    return 1;
  }

  /**
   * <pre>绑定房产</pre>
   *
   * @param houseId  房产Id
   * @param userId   用户Id
   * @param bookmark 是否是收藏类型
   * @return
   */
  public int bindUser2House(Long houseId, Long userId, HouseUserTypeEnum bookmark) {
    HouseUserReq houseUserReq = new HouseUserReq();
    houseUserReq.setHouseId(houseId);
    houseUserReq.setUserId(userId);
    houseUserReq.setUnbind(true);
    houseUserReq.setBindType(bookmark.value);
    String result = RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(houseServiceName, "/house/bind");
      ResponseEntity<RestResponse<String>> responseEntity = rest.post(url, houseUserReq,
              new ParameterizedTypeReference<RestResponse<String>>() {
              });
      return responseEntity.getBody();
    }).getResult();
    log.info("Rest Call BindUser2House Result={}", result);
    return 1;
  }

  /**
   * <pre>解除绑定</pre>
   *
   * @param houseId  房产Id
   * @param userId   用户Id
   * @param bookmark 是否是收藏类型
   * @return
   */
  public int unbindUser2House(Long houseId, Long userId, HouseUserTypeEnum bookmark) {
    HouseUserReq houseUserReq = new HouseUserReq();
    houseUserReq.setHouseId(houseId);
    houseUserReq.setUserId(userId);
    houseUserReq.setUnbind(true);
    houseUserReq.setBindType(bookmark.value);
    String result = RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(houseServiceName, "/house/bind");
      ResponseEntity<RestResponse<String>> responseEntity = rest.post(url, houseUserReq,
              new ParameterizedTypeReference<RestResponse<String>>() {
              });
      return responseEntity.getBody();
    }).getResult();
    log.info("Rest Call UnbindUser2House Result={}", result);
    return 1;
  }

  /**
   * <pre>查询所有的城市</pre>
   *
   * @return
   */
  public List<CityDO> getAllCities() {
    return RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(houseServiceName, "/house/cities");
      ResponseEntity<RestResponse<List<CityDO>>> responseEntity = rest.get(url,
              new ParameterizedTypeReference<RestResponse<List<CityDO>>>() {
              });
      return responseEntity.getBody();
    }).getResult();
  }

  /**
   * <pre>查询所有的社区</pre>
   *
   * @return
   */
  public List<CommunityDO> getAllCommunities() {
    return RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(houseServiceName, "/house/communities");
      ResponseEntity<RestResponse<List<CommunityDO>>> responseEntity = rest.get(url,
              new ParameterizedTypeReference<RestResponse<List<CommunityDO>>>() {
              });
      return responseEntity.getBody();
    }).getResult();
  }

  /**
   * <pre>分页查询</pre>
   *
   * @param query    查询条件封装
   * @param pageNum  当前页
   * @param pageSize 每页显示大小
   * @return
   */
  public PageListResponse<HouseDO> queryHouse(HouseDO query, Integer pageNum, Integer pageSize) {
    HouseQueryReq houseQueryReq = new HouseQueryReq();
    houseQueryReq.setQuery(query);
    houseQueryReq.setPageNum(pageNum);
    houseQueryReq.setPageSize(pageSize);
    return RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(houseServiceName, "/house/list");
      ResponseEntity<RestResponse<PageListResponse<HouseDO>>> responseEntity = rest.post(url, houseQueryReq,
              new ParameterizedTypeReference<RestResponse<PageListResponse<HouseDO>>>() {
              });
      return responseEntity.getBody();
    }).getResult();
  }

  /**
   * <pre>添加房产</pre>
   *
   * @param house
   * @return
   */
  public int addHouse(HouseDO house) {
    String result = RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(houseServiceName, "/house/add");
      ResponseEntity<RestResponse<String>> responseEntity = rest.post(url,house,
              new ParameterizedTypeReference<RestResponse<String>>() {
              });
      return responseEntity.getBody();
    }).getResult();
    log.info("Rest Call Add House Result={}", result);
    return 1;
  }
}
