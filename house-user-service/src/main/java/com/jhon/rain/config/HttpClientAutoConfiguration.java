package com.jhon.rain.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.httpclient.LogbookHttpRequestInterceptor;
import org.zalando.logbook.httpclient.LogbookHttpResponseInterceptor;

/**
 * <p>功能描述</br>自动配置类</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/20 21:24
 */
@Configuration
@ConditionalOnClass({HttpClient.class})
@EnableConfigurationProperties(HttpClientProperties.class)
public class HttpClientAutoConfiguration {

  private final HttpClientProperties properties;

  public HttpClientAutoConfiguration(HttpClientProperties properties) {
    this.properties = properties;
  }

  @Autowired
  private LogbookHttpRequestInterceptor logbookHttpRequestInterceptor;

  @Autowired
  private LogbookHttpResponseInterceptor logbookHttpResponseInterceptor;

  /**
   * <pre>重新定义HttpClient(httpclient bean 的定义)</pre>
   *
   * @return
   */
  @Bean
  @ConditionalOnMissingBean(HttpClient.class)
  public HttpClient httpClient() {
    /** 构建requestConfig **/
    RequestConfig requestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(properties.getConnectTimeOut())
            .setSocketTimeout(properties.getSocketTimeOut()).build();

    HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig)
            .setUserAgent(properties.getAgent())
            .setMaxConnPerRoute(properties.getMaxConnPerRoute())
            .setMaxConnTotal(properties.getMaxConnTotal())
            .addInterceptorFirst(logbookHttpRequestInterceptor)
            .addInterceptorFirst(logbookHttpResponseInterceptor)
            .build();

    return client;
  }
}
