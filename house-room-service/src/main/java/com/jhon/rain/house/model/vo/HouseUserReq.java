package com.jhon.rain.house.model.vo;

import lombok.Data;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 11:03
 */
@Data
public class HouseUserReq {

  private Long houseId;

  private Long userId;

  private Integer bindType;

  private boolean unbind;

}
