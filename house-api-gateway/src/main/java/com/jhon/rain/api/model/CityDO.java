package com.jhon.rain.api.model;

import lombok.Data;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/15 19:45
 */
@Data
public class CityDO {

  private Integer id;

  /**
   * 城市名称
   */
  private String cityName;
  /**
   * 城市编码
   */
  private String cityCode;

}
