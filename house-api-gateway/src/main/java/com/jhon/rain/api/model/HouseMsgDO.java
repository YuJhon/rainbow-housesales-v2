package com.jhon.rain.api.model;

import java.util.Date;

/**
 * <pre>房产信息</pre>
 */
public class HouseMsgDO {

  /** 主键 **/
  private Long id;

  /** 消息 **/
  private String msg;

  /** 创建时间 **/
  private Date createTime;

  /** 所属经纪人机构 **/
  private Long agentId;

  /** 房屋Id **/
  private Long houseId;

  /** 用户名 **/
  private String userName;

  /** 用户Id **/
  private Long userId;

  /** 邮箱 **/
  private String email;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg == null ? null : msg.trim();
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Long getAgentId() {
    return agentId;
  }

  public void setAgentId(Long agentId) {
    this.agentId = agentId;
  }

  public Long getHouseId() {
    return houseId;
  }

  public void setHouseId(Long houseId) {
    this.houseId = houseId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName == null ? null : userName.trim();
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}