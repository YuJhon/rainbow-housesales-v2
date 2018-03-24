package com.jhon.rain.api.model.vo;

import com.jhon.rain.api.model.BlogDO;
import lombok.Data;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 19:35
 */
@Data
public class BlogQueryReq {

  private BlogDO blog;

  private Integer pageSize;

  private Integer pageNum;

}
