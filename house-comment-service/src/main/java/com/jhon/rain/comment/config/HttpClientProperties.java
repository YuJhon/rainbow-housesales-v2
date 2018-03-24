package com.jhon.rain.comment.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>功能描述</br>Httpclient的属性配置</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/20 21:17
 */
@ConfigurationProperties(prefix = "spring.httpclient")
public class HttpClientProperties {

  private Integer connectTimeOut = 1000;

  private Integer socketTimeOut = 1000000;

  private String agent = "agent";

  private Integer maxConnPerRoute = 10;

  private Integer maxConnTotal = 50;


  public Integer getConnectTimeOut() {
    return connectTimeOut;
  }

  public void setConnectTimeOut(Integer connectTimeOut) {
    this.connectTimeOut = connectTimeOut;
  }

  public Integer getSocketTimeOut() {
    return socketTimeOut;
  }

  public void setSocketTimeOut(Integer socketTimeOut) {
    this.socketTimeOut = socketTimeOut;
  }

  public String getAgent() {
    return agent;
  }

  public void setAgent(String agent) {
    this.agent = agent;
  }

  public Integer getMaxConnPerRoute() {
    return maxConnPerRoute;
  }

  public void setMaxConnPerRoute(Integer maxConnPerRoute) {
    this.maxConnPerRoute = maxConnPerRoute;
  }

  public Integer getMaxConnTotal() {
    return maxConnTotal;
  }

  public void setMaxConnTotal(Integer maxConnTotal) {
    this.maxConnTotal = maxConnTotal;
  }
}
