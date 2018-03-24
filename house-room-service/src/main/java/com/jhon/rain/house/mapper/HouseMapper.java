package com.jhon.rain.house.mapper;

import com.jhon.rain.house.model.HouseDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 10:54
 */
@Mapper
public interface HouseMapper {
  /**
   * <pre>下架房产</pre>
   *
   * @param houseId
   * @return
   */
  int downHouse(@Param("id") Long houseId);

  /**
   * <pre>添加房产</pre>
   *
   * @param house
   * @return
   */
  int insert(HouseDO house);

  /**
   * <pre>查询房产列表</pre>
   *
   * @param query
   * @return
   */
  List<HouseDO> queryHouseList(HouseDO query);

  /**
   * <pre>查询房产详细信息</pre>
   *
   * @param id 房间主键ID
   * @return
   */
  HouseDO queryDetailById(Long id);

  /**
   * <pre>更新房产</pre>
   *
   * @param updateHouse
   * @return
   */
  int updateHouseRating(HouseDO updateHouse);
}
