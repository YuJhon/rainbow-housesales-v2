package com.jhon.rain.house.service;

import com.jhon.rain.house.model.HouseDO;

import java.util.List;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 12:00
 */
public interface RecommendService {
  /**
   * <pre>查询最热的房产</pre>
   *
   * @param size
   * @return
   */
  List<HouseDO> getHotHouse(Integer size);

  /**
   * <pre>查询最新的房产</pre>
   *
   * @return
   */
  List<HouseDO> getLatestHouse();

  /**
   * <pre>新增热度</pre>
   * @param houseId
   */
  void increaseHot(long houseId);

}
