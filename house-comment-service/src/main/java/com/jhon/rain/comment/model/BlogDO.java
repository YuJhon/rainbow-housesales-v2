package com.jhon.rain.comment.model;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <pre>博客实体</pre>
 */
@Data
public class BlogDO {
  /**
   * 主键
   */
  private Integer id;

  /**
   * 标签
   **/
  private String tags;

  /**
   * 标题
   **/
  private String title;

  /**
   * 内容
   **/
  private String content;

  /**
   * 创建时间
   **/
  private Date createTime;

  /**
   * 分类：1-准备买房，2-看房/选房，3-签约/定房，4-全款/贷款，5-缴税/过户，6-入住/交接，7-买房风险
   **/
  private Integer cat;

  /**
   * 摘要
   **/
  private String digest;
  /**
   * 标签列表
   **/
  private List<String> tagList = Lists.newArrayList();

}