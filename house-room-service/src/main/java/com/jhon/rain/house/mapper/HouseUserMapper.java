package com.jhon.rain.house.mapper;

import com.jhon.rain.house.model.HouseUserDO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 12:33
 */
public interface HouseUserMapper {
  /**
   * <pre>解除房产关系</pre>
   *
   * @param houseId 房产Id
   * @param userId  用户Id
   * @param type    类型
   * @return
   */
  int deleteHouseUser(@Param("houseId") Long houseId, @Param("userId") Long userId, @Param("type") Integer type);

  /**
   * <pre>查询用户和房产关系记录</pre>
   *
   * @param houseId 房产Id
   * @param userId  用户ID
   * @param type    类型
   * @return
   */
  HouseUserDO queryHouseUser(@Param("houseId") Long houseId, @Param("userId") Long userId, @Param("type") Integer type);

  /**
   * <pre>添加房产和用户关系</pre>
   *
   * @param houseUser
   * @return
   */
  int insertHouseUser(HouseUserDO houseUser);
}
