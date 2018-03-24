package com.jhon.rain.house.enmus;

/**
 * <p>功能描述</br>用户类型枚举</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/12 22:24
 */
public enum HouseUserTypeEnum {

  SALE(1), BOOKMARK(2);

  public Integer value;

  private HouseUserTypeEnum(Integer value) {
    this.value = value;
  }
}
