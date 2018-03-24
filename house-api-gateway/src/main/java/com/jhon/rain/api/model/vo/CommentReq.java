package com.jhon.rain.api.model.vo;

import lombok.Data;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 19:22
 */
@Data
public class CommentReq {

  private Long userId;

  private Long houseId;

  private Integer blogId;

  private String content;

  private Integer type; //1-房产，2-博客百科

  private Integer size; //获取多少评论
}
