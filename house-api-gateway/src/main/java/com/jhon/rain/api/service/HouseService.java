package com.jhon.rain.api.service;

import com.jhon.rain.api.HouseUserTypeEnum;
import com.jhon.rain.api.model.*;
import com.jhon.rain.api.page.PageData;

import java.util.List;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 14:53
 */
public interface HouseService {
  /**
   * <pre>查询最热的房产</pre>
   *
   * @param size
   * @return
   */
  List<HouseDO> getHotHouses(int size);

  /**
   * <pre>分页查询</pre>
   *
   * @param query
   * @param pageNum
   * @param pageSize
   * @return
   */
  PageData<HouseDO> queryHouse(HouseDO query, Integer pageNum, Integer pageSize);

  /**
   * <pre>查询最新的房产</pre>
   *
   * @param size
   * @return
   */
  List<HouseDO> getLatestHouse(int size);

  /**
   * <pre>通过主键查询房间信息</pre>
   *
   * @param houseId
   * @return
   */
  HouseDO queryHouseById(Long houseId);

  /**
   * <pre>添加房产留言</pre>
   *
   * @param houseMsg
   * @return
   */
  int addHouseMsg(HouseMsgDO houseMsg);

  /**
   * <pre>房产评级</pre>
   *
   * @param id
   * @param rating
   * @return
   */
  int updateRating(Long id, Double rating);

  /**
   * <pre>绑定房产</pre>
   *
   * @param houseId  房产Id
   * @param userId   用户Id
   * @param bookmark 房产用户类型
   * @return
   */
  int bindUser2House(Long houseId, Long userId, HouseUserTypeEnum bookmark);

  /**
   * <pre>房产解绑</pre>
   *
   * @param houseId  房产id
   * @param userId   用户id
   * @param bookmark 房产用户类型
   * @return
   */
  int unbindUser2House(Long houseId, Long userId, HouseUserTypeEnum bookmark);

  /**
   * <pre>获取城市列表</pre>
   *
   * @return
   */
  List<CityDO> getAllCities();

  /**
   * <pre>获取所有的小区</pre>
   *
   * @return
   */
  List<CommunityDO> getAllCommunities();

  /**
   * <pre>添加房产</pre>
   *
   * @param house 房产
   * @param user  当前操作用户
   * @return
   */
  int addHouse(HouseDO house, UserDO user);
}
