package com.jhon.rain.house.mapper;

import com.jhon.rain.house.model.HouseMsgDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 12:33
 */
@Mapper
public interface HouseMsgMapper {

  /**
   * <pre>新增房产留言</pre>
   *
   * @param houseMsg
   * @return
   */
  int addHouseMsg(HouseMsgDO houseMsg);
}
