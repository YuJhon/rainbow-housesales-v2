package com.jhon.rain.house.model.vo;

import com.jhon.rain.house.model.HouseDO;
import lombok.Data;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 11:39
 */
@Data
public class HouseQueryReq {

  private HouseDO query;

  private Integer pageNum;

  private Integer pageSize;
}
