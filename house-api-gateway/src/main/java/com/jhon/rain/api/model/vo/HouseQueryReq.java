package com.jhon.rain.api.model.vo;

import com.jhon.rain.api.model.HouseDO;
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
