package com.jhon.rain.user.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/21 21:39
 */
@Data
public class UserDO {

  /**
   * 主键
   **/
  private Long id;

  /**
   * 邮箱
   **/
  private String email;

  /**
   * 电话
   **/
  private String phone;

  /**
   * 用户名
   **/
  private String name;

  /**
   * 密码
   **/
  private String passwd;

  /**
   * 用户类型：（1:普通用户 2:经纪人）
   **/
  private Integer type;//

  /**
   * 创建时间
   **/
  private Date createTime;

  /**
   * 是否启用(1:是 0:否)
   **/
  private Integer enable;

  /**
   * 头像
   **/
  private String avatar;

  /**
   * 机构Id
   **/
  private Integer agencyId;

  /**
   * 介绍
   **/
  private String aboutme;

  /** 非DB字段 **/

  /**
   * 机构名称
   **/
  private String agencyName;

  /**
   * 头像文件
   **/
  private MultipartFile avatarFile;

  /**
   * 新密码
   **/
  private String newPassword;

  /**
   * 缓存用户邮箱对应的key
   **/
  private String key;

  /**
   * 修改密码时候的确认密码
   **/
  private String confirmPasswd;

  /**
   * 激活链接
   */
  private String enableUrl;

  /**
   * 登录认证token
   */
  private String token;
}
