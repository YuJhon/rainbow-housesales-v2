package com.jhon.rain.house.service;

import com.jhon.rain.house.model.CityDO;

import java.util.List;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 11:15
 */
public interface CityService {
  /**
   * <pre>查询所有的城市</pre>
   *
   * @return
   */
  List<CityDO> queryAll();

}
