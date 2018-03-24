package com.jhon.rain.comment.model;

import lombok.Data;

import java.util.Date;

/**
 * <pre>评论表</pre>
 */
@Data
public class CommentDO {

  /**
   * 主键
   **/
  private Long id;

  /**
   * 内容
   **/
  private String content;

  /**
   * 房产ID
   **/
  private Long houseId;

  /**
   * 博客ID
   **/
  private Integer blogId;

  /**
   * 类型 1-房产评论，2-博客评论
   **/
  private Integer type;

  /**
   * 用户
   **/
  private Long userId;

  /**
   * 发布时间
   **/
  private Date createTime;

  /**
   * 非DB字段
   **/

  /**
   * 用户名
   **/
  private String username;

  /**
   * 头像
   **/
  private String avatar;
}