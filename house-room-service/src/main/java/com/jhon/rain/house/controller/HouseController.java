package com.jhon.rain.house.controller;

import com.jhon.rain.house.common.CommonConstants;
import com.jhon.rain.house.common.RestCode;
import com.jhon.rain.house.common.RestResponse;
import com.jhon.rain.house.enmus.HouseUserTypeEnum;
import com.jhon.rain.house.model.CityDO;
import com.jhon.rain.house.model.CommunityDO;
import com.jhon.rain.house.model.HouseDO;
import com.jhon.rain.house.model.HouseMsgDO;
import com.jhon.rain.house.model.dto.PageListResponse;
import com.jhon.rain.house.model.vo.HouseQueryReq;
import com.jhon.rain.house.model.vo.HouseUserReq;
import com.jhon.rain.house.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * <p>功能描述</br>房产服务控制器</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 10:52
 */
@RestController
@RequestMapping("house")
public class HouseController {

  @Autowired
  private HouseService houseService;

  @Autowired
  private CityService cityService;

  @Autowired
  private CommunityService communityService;

  @Autowired
  private HouseMsgService houseMsgService;

  @Autowired
  private RecommendService recommendService;

  /**
   * <pre>分页查询房产信息</pre>
   *
   * @param req
   * @return
   */
  @PostMapping("list")
  public RestResponse<PageListResponse<HouseDO>> houseList(@RequestBody HouseQueryReq req) {
    Integer pageNum = req.getPageNum();
    Integer pageSize = req.getPageSize();
    HouseDO query = req.getQuery();
    PageListResponse<HouseDO> pageListResponse = houseService.queryHouseList(query, pageNum, pageSize);
    return RestResponse.success(pageListResponse);
  }

  /**
   * <pre>添加房产服务</pre>
   *
   * @param house 房产
   * @return
   */
  @PostMapping("add")
  public RestResponse<String> addHouse(@RequestBody HouseDO house) {
    house.setState(CommonConstants.HOUSE_STATE_UP);
    if (house.getUserId() == null) {
      return RestResponse.error(RestCode.ILLEGAL_PARAMS);
    }
    houseService.addHouse(house);
    return RestResponse.success();
  }

  /**
   * <pre>查询房产的详情</pre>
   *
   * @param id 房产Id
   * @return
   */
  @GetMapping("detail")
  public RestResponse<HouseDO> queryDetail(@RequestParam(name = "id") Long id) {
    HouseDO house = houseService.queryDetailById(id);
    /** 热度添加 **/
    recommendService.increaseHot(id);
    return RestResponse.success(house);
  }

  /**
   * <pre>房产的绑定和解绑</pre>
   *
   * @param req
   * @return
   */
  @PostMapping("bind")
  public RestResponse<String> bindHouse(@RequestBody HouseUserReq req) {
    /** 获取绑定房产的类型 **/
    Integer bindType = req.getBindType();
    Long userId = req.getUserId();
    Long houseId = req.getHouseId();
    HouseUserTypeEnum houseUserType = Objects.equals(bindType, 1) ? HouseUserTypeEnum.SALE : HouseUserTypeEnum.BOOKMARK;
    if (req.isUnbind()) {
      houseService.unBindUser2House(houseId, userId, houseUserType);
    } else {
      houseService.bindUser2House(houseId, userId, houseUserType);
    }
    return RestResponse.success();
  }

  /**
   * <pre>留言</pre>
   *
   * @param houseMsg
   * @return
   */
  @PostMapping("addMsg")
  public RestResponse<String> houseMsg(@RequestBody HouseMsgDO houseMsg) {
    houseMsgService.addHouseMsg(houseMsg);
    return RestResponse.success();
  }

  /**
   * <pre>更新评分</pre>
   *
   * @param rating  登记
   * @param houseId 房产id
   * @return
   */
  @GetMapping("rating")
  public RestResponse<String> houseRate(@RequestParam(name = "rating") Double rating,
                                        @RequestParam(name = "id") Long houseId) {
    houseService.updateRating(houseId, rating);
    return RestResponse.success();
  }

  /**
   * <pre>查询所有的城市</pre>
   *
   * @return
   */
  @GetMapping("cities")
  public RestResponse<List<CityDO>> queryAllCities() {
    List<CityDO> cities = cityService.queryAll();
    return RestResponse.success(cities);
  }

  /**
   * <pre>查询所有的小区</pre>
   *
   * @return
   */
  @GetMapping("communities")
  public RestResponse<List<CommunityDO>> queryAllCommunities() {
    List<CommunityDO> communities = communityService.queryAll();
    return RestResponse.success(communities);
  }

  /**
   * <pre>查询最热的房产</pre>
   *
   * @param size 每次查询的条数
   * @return
   */
  @GetMapping("hot")
  public RestResponse<List<HouseDO>> getHotHouse(@RequestParam(name = "size") Integer size) {
    List<HouseDO> hotHouses = recommendService.getHotHouse(size);
    return RestResponse.success(hotHouses);
  }

  /**
   * <pre>查询最新的房产</pre>
   *
   * @return
   */
  @GetMapping("latest")
  public RestResponse<List<HouseDO>> getLatest() {
    List<HouseDO> latestHouses = recommendService.getLatestHouse();
    return RestResponse.success(latestHouses);
  }
}
