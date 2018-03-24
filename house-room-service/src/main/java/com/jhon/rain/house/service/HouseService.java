package com.jhon.rain.house.service;

import com.jhon.rain.house.enmus.HouseUserTypeEnum;
import com.jhon.rain.house.model.HouseDO;
import com.jhon.rain.house.model.dto.PageListResponse;

import java.util.List;

/**
 * <p>功能描述</br>房产接口</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 10:52
 */
public interface HouseService {
  /**
   * <pre>房产解绑</pre>
   *
   * @param houseId       房产Id
   * @param userId        用户ID
   * @param houseUserType 房产用户类型
   * @return
   */
  int unBindUser2House(Long houseId, Long userId, HouseUserTypeEnum houseUserType);

  /**
   * <pre>房产绑定</pre>
   *
   * @param houseId       房产Id
   * @param userId        用户ID
   * @param houseUserType 房产用户类型
   * @return
   */
  int bindUser2House(Long houseId, Long userId, HouseUserTypeEnum houseUserType);

  /**
   * <pre>房产添加</pre>
   *
   * @param house 房产信息
   * @return
   */
  int addHouse(HouseDO house);

  /**
   * <pre>分页查询房产信息</pre>
   *
   * @param query    附加条件
   * @param pageNum  当前页
   * @param pageSize 每页显示条数
   * @return
   */
  PageListResponse<HouseDO> queryHouseList(HouseDO query, Integer pageNum, Integer pageSize);

  /**
   * <pre>查询房产的详细信息</pre>
   *
   * @param id
   * @return
   */
  HouseDO queryDetailById(Long id);

  /**
   * <pre>更新等级</pre>
   *
   * @param houseId
   * @param rating
   * @return
   */
  int updateRating(Long houseId, Double rating);

  /**
   * <pre>查询房产并且设置图片</pre>
   *
   * @param query
   * @param size
   * @return
   */
  List<HouseDO> queryHouseAndSetImg(HouseDO query,Integer size);
}
