package com.jhon.rain.api.model;

import lombok.Data;

/**
 * <p>功能描述</br>经纪机构实体</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/22 12:53
 */
@Data
public class AgencyDO {
  /**
   * 主键ID
   */
  private Integer id;
  /**
   * 名称
   */
  private String name;
  /**
   * 地址
   */
  private String address;
  /**
   * 手机
   */
  private String phone;
  /**
   * 邮箱
   */
  private String email;
  /**
   * 简介
   */
  private String aboutUs;
  /**
   * 电话
   */
  private String mobile;
  /**
   * 网址
   */
  private String webSite;
}
