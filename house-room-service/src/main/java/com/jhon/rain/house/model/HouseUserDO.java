package com.jhon.rain.house.model;

import lombok.Data;

import java.util.Date;

/**
 * <pre>房产和用户的关联关系</pre>
 */
@Data
public class HouseUserDO {

  /**
   * 主键Id
   */
  private Long id;

  /**
   * 房产Id
   */
  private Long houseId;

  /**
   * 用户Id
   */
  private Long userId;

  /**
   * 用户类型：1 普通用户  2：经纪人
   */
  private Integer type;

  /**
   * 创建时间
   */
  private Date createTime;

}