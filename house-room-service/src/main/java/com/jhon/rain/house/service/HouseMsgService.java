package com.jhon.rain.house.service;

import com.jhon.rain.house.model.HouseMsgDO;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 11:56
 */
public interface HouseMsgService {
  /**
   * <pre>添加房产信息</pre>
   *
   * @param houseMsg
   * @return
   */
  int addHouseMsg(HouseMsgDO houseMsg);
}
