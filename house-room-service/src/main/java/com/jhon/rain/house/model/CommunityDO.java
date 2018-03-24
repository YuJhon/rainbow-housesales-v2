package com.jhon.rain.house.model;

import lombok.Data;

/**
 * <pre>社区实体</pre>
 */
@Data
public class CommunityDO {
  /**
   * 主键
   */
  private Integer id;

  /**
   * 城市编码
   **/
  private String cityCode;

  /**
   * 社区名称
   **/
  private String name;

  /**
   * 城市名称
   **/
  private String cityName;
}