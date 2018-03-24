package com.jhon.rain.comment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * <p>功能描述</br>Rest调用封装[既支持直连又支持服务发现的调用]</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/20 21:49
 */
@Component
public class GenericRest {

  @Autowired
  private RestTemplate lbRestTemplate;

  @Autowired
  private RestTemplate directRestTemplate;

  private static final String DIRECT_FLAG = "direct://";

  /**
   * <pre>Post请求方式</pre>
   *
   * @param url
   * @param reqBody
   * @param responseType
   * @param <T>
   * @return
   */
  public <T> ResponseEntity<T> post(String url, Object reqBody, ParameterizedTypeReference<T> responseType) {
    RestTemplate template = getRestTemplate(url);
    url = url.replace(DIRECT_FLAG, "");
    return template.exchange(url, HttpMethod.POST, new HttpEntity(reqBody), responseType);
  }

  /**
   * <pre>Get请求方式</pre>
   *
   * @param url
   * @param responseType
   * @param <T>
   * @return
   */
  public <T> ResponseEntity<T> get(String url, ParameterizedTypeReference<T> responseType) {
    RestTemplate template = getRestTemplate(url);
    url = url.replace(DIRECT_FLAG, "");
    return template.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, responseType);
  }

  /**
   * <pre>获取resttemplate</pre>
   *
   * @param url
   * @return
   */
  private RestTemplate getRestTemplate(String url) {
    if (url.contains(DIRECT_FLAG)) {
      return directRestTemplate;
    } else {
      return lbRestTemplate;
    }
  }
}
