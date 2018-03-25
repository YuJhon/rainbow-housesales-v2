# 房产销售平台--微服务化
> 　　去年的时候新建过一个业余项目[microservice-rainbow](https://github.com/YuJhon/microservice-rainbow)来学习Spring cloud的一些基础组件的使用，但是没有在实际项目中应用过，有些东西还是停留在了理论阶段，现在正好有一个业余的项目来实战操作，以此来加强对SpringCloud微服务架构的了解和应用。
## 项目初始化预览
* 1.注册中心搭建完成
![1.注册中心搭建完成](./photos/1.Eureka服务注册中心搭建完成.png)

* 2.Eureka-Server项目结构
![2.Eureka-Server项目结构](./photos/2.Eureka-Server-Project-Config01.png)

* 3.Eureka-Server项目配置文件
![3.Eureka-Server项目配置文件](./photos/3.Eureka-Server-Project-Properties.png)

* 4.Eureka-Client项目结构
![4.Eureka-Client项目结构](./photos/4.Eureka-Client-Project-Config01.png)

* 5.Eureka-Client项目配置文件
![5.Eureka-Client项目配置文件](./photos/5.Eureka-Client-Properties.png)

* 6.Api-Gateway项目结构
![6.Api-Gateway项目结构](./photos/6.Eureka-API-Gateway-Project.png)

* 7.Api-Gateway项目配置文件
![7.Api-Gateway项目配置文件](./photos/7.Eureka-API-Gateway-Properties.png)

* 更新日期：2018-03-20

## 服务通信组件RestTemplate + httpclient的二次封装
* 添加基础依赖
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.31</version>
</dependency>
<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpclient</artifactId>
</dependency>
```

* HttpClient属性的设置(通过配置文件来定义)
```java
package com.jhon.rain.config;

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

```

* HttpClient的重新封装
```java
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

```

* Rest调用封装[既支持直连又支持服务发现的调用]
```java
package com.jhon.rain.config;

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

```
## Logbook的引入和http日志输出
* 添加Maven依赖([Logbook介绍](https://github.com/zalando/logbook))
```xml
<dependency>
    <groupId>org.zalando</groupId>
    <artifactId>logbook-core</artifactId>
    <version>${logbook.version}</version>
</dependency>
<dependency>
    <groupId>org.zalando</groupId>
    <artifactId>logbook-servlet</artifactId>
    <version>${logbook.version}</version>
</dependency>
<dependency>
    <groupId>org.zalando</groupId>
    <artifactId>logbook-httpclient</artifactId>
    <version>${logbook.version}</version>
</dependency>
<dependency>
    <groupId>org.zalando</groupId>
    <artifactId>logbook-spring-boot-starter</artifactId>
    <version>${logbook.version}</version>
</dependency>

<dependency> <!-- exclude掉spring-boot的默认log配置 -->
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
<!--开启log4j2的异步日志必须要依赖的分布式高性能框架-->
<dependency>
    <groupId>com.lmax</groupId>
    <artifactId>disruptor</artifactId>
    <version>3.3.6</version>
</dependency>
```
* HttpClient Bean的封装和Logbook请求和响应拦截器的配置
![HttpClient Bean的封装和Logbook请求和响应拦截器的配置](./photos/9.HttpClient%20Bean的封装和Logbook请求和响应拦截器的配置.png)

* Logbook配置日志的级别和日志的格式
![Logbook的配置](./photos/10.Logbook的配置.png)

    ```properties
    logging.config=classpath:log4j2.xml
    logbook.write.level=INFO
    logbook.format.style=http
    ```
    
* RestTemplate Bean的封装[加入消息转换器]
```java
package com.jhon.rain.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import org.apache.http.client.HttpClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * <p>功能描述</br>RestTemplate Bean的封装[加入消息转换器]</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/20 21:40
 */
@Configuration
public class RestAutoConfig {

  public static class RestTemplateConfig {

    @Bean
    @LoadBalanced
    RestTemplate lbRestTemplate(HttpClient httpClient) {
      RestTemplate template = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
      template.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("utf-8")));
      template.getMessageConverters().add(1, new FastJsonHttpMessageConvert5());
      return template;
    }

    @Bean
    RestTemplate directRestTemplate(HttpClient httpclient) {
      RestTemplate template = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpclient));
      template.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("utf-8")));
      template.getMessageConverters().add(1, new FastJsonHttpMessageConvert5());
      return template;
    }

    public static class FastJsonHttpMessageConvert5 extends FastJsonHttpMessageConverter4 {

      static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

      public FastJsonHttpMessageConvert5() {
        setDefaultCharset(DEFAULT_CHARSET);
        setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, new MediaType("application", "*+json")));
      }

    }
  }
}
```

* 服务网关(House-API-Gateway)
    * [1].UserAPIController.java
    ```java
    package com.jhon.rain.api.controller;
    
    import com.jhon.rain.api.service.UserService;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;
    
    /**
     * <p>功能描述</br></p>
     *
     * @author jiangy19
     * @version v1.0
     * @projectName rainbow-house-v2
     * @date 2018/3/20 22:47
     */
    @RestController
    @RequestMapping("api/user")
    @Slf4j
    public class UserAPIController {
    
      @Autowired
      private UserService userService;
    
      @GetMapping("getUsername/{id}")
      public String getUsername(@PathVariable(name = "id") Long id) {
        log.info("API-Gateway Controller Request Comming!");
        return userService.getUsername(id);
      }
    }
    
    ```
    * [2].UserService.java
    ```java
    package com.jhon.rain.api.service;
    
    import com.jhon.rain.api.dao.UserDao;
    import com.netflix.discovery.converters.Auto;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    
    /**
     * <p>功能描述</br></p>
     *
     * @author jiangy19
     * @version v1.0
     * @projectName rainbow-house-v2
     * @date 2018/3/20 22:47
     */
    @Service
    @Slf4j
    public class UserService {
    
      @Autowired
      private UserDao userDao;
    
      /**
       * <pre>获取用户名</pre>
       *
       * @param id 用户id
       * @return
       */
      public String getUsername(Long id) {
        return userDao.getUserNameById(id);
      }
    }
    ```
    * [3].UserDao.java
    ```java
    package com.jhon.rain.api.dao;
    
    import com.jhon.rain.api.common.RestResponse;
    import com.jhon.rain.api.config.GenericRest;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.core.ParameterizedTypeReference;
    import org.springframework.stereotype.Repository;
    
    /**
     * <p>功能描述</br></p>
     *
     * @author jiangy19
     * @version v1.0
     * @projectName rainbow-house-v2
     * @date 2018/3/20 22:38
     */
    @Repository
    public class UserDao {
    
      @Autowired
      private GenericRest rest;
    
      @Value("${user.service.name}")
      private String userServiceName;
    
      /**
       * <pre>调用服务</pre>
       *
       * @param id 用户id
       * @return 返回用户名
       */
      public String getUserNameById(Long id) {
        String url = "http://" + userServiceName + "/user/getUsername?id=" + id;
        RestResponse<String> restResponse = rest.get(url, new ParameterizedTypeReference<RestResponse<String>>() {
        }).getBody();
        if (restResponse.getCode() == 0) {
          return restResponse.getResult();
        } else {
          return null;
        }
      }
    }
    ```
    
* 用户服务提供接口(House-User-Service)
    * UserController.java
    ```java
    package com.jhon.rain.controller;
    
    import com.jhon.rain.user.common.RestResponse;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
    
    /**
     * <p>功能描述</br></p>
     *
     * @author jiangy19
     * @version v1.0
     * @projectName rainbow-house-v2
     * @date 2018/3/20 22:27
     */
    @RestController
    @Slf4j
    @RequestMapping("user")
    public class UserController {
    
      @RequestMapping("getUsername")
      public RestResponse<String> getUserName(Long id) {
        log.info("In Coming Request!");
        return RestResponse.success("test-name");
      }
    }
    ```
    
* 结果展示
    * 整合Logbook日志框架后的API-Gateway项目HTTP调用(Rest工具)
    ![整合Logbook日志框架后的API-Gateway项目HTTP调用](./photos/8.整合Logbook日志框架后的API-Gateway项目HTTP调用.png)
    * House-User-Service提供的日志记录
    ![House-User-Service提供的日志记录](./photos/11.House-User-Service提供的日志记录.png)
    * House-API-Gateway提供的日志记录
    ![House-API-Gateway提供的日志记录](./photos/12.House-API-Gateway提供的日志记录.png)
    
    * 整合Logbook日志框架后的API-Gateway项目Curl工具调用
    ![House-API-Gateway提供的日志记录（Curl）](./photos/13.House-API-Gateway提供的日志记录（Curl）.png)
    * House-API-Gateway提供的日志记录
    ![House-API-Gateway提供的日志记录（Console）](./photos/14.House-API-Gateway提供的日志记录（Console）.png)
        
* 更新日期：2018-03-21 12:00:00

## [Spring Cloud Ribbon]负载均衡组件引入
* Maven依赖(House-API-Gateway)
    * 【注意】由于Spring-Cloud-Starter-Eureka-Server中已经默认将Ribbon的依赖引入了，所以我们在项目中不需要再次单独将Ribbon的依赖添加到pom文件中。
    * Starter-Eureka-Server中默认添加了Ribbon的依赖，如下图所示：
        ![整合Ribbon实现负载均衡（Maven依赖）](./photos/15.整合Ribbon实现负载均衡（Maven依赖）.png)
        
* RestTemplate添加@LoadBalanced注解
![整合Ribbon实现负载均衡（RestTemplate注解）](./photos/16.整合Ribbon实现负载均衡（RestTemplate注解）.png)
    
* 模拟请求（每次id不同）
![模拟客户端的三次请求](./photos/19.模拟客户端的三次请求.png)

* 请求结果展示
    * House-User-Service 8090实例Console日志
    ![8090实例的Console日志](./photos/20.用户实例(House-User-Service)8090实例的Console日志.png)
    * House-User-Service 8091实例console日志
    ![8091实例的Console日志](./photos/21.用户实例(House-User-Service)8091实例的Console日志.png)
    
* 如果想使用Ribbon而不依赖于Eureka，则参考一下文档:
    [spring-cloud-ribbon-without-eureka](http://projects.spring.io/spring-cloud/spring-cloud.html#spring-cloud-ribbon-without-eureka)
    
    
* 更新日期：2018-03-23    
    
## Spring Cloud Hystrix 使用和测试场景
* Hystrix 简介
    [点击这里]()

* 服务消费端【House-API-Gateway】添加Hystrix依赖
    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-eureka</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-hystrix</artifactId>
    </dependency>
    ```
* Hystrix配置
    ```xml
    #Hystrix配置
    hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=1
    hystrix.threadpool.default.coreSize=5
    hystrix.threadpool.default.maxQueueSize=1
    hystrix.threadpool.default.maximumSize=10
    
    hystrix.command.default.circuitBreaker.errorThresholdPercentage=10
    #断路器的时间窗口设置为10s
    hystrix.command.default.circuitBreaker.sleepWindowInMilliseconds=10000
    ```

* AccountsDao.java 添加@HystrixCommand注解
    ```java
    package com.jhon.rain.api.dao;
    
    import com.jhon.rain.api.common.RestResponse;
    import com.jhon.rain.api.config.GenericRest;
    import com.jhon.rain.api.model.UserDO;
    import com.jhon.rain.api.util.RestHelper;
    import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
    import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
    import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.core.ParameterizedTypeReference;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Repository;
    
    import java.lang.reflect.Type;
    import java.util.List;
    
    /**
     * <p>功能描述</br>用户账号信息的数据访问层</p>
     *
     * @author jiangy19
     * @version v1.0
     * @projectName rainbow-house-v2
     * @date 2018/3/23 9:39
     */
    @Slf4j
    @Repository
    @DefaultProperties(groupKey = "userDao", threadPoolKey = "userDao",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")},
            threadPoolProperties = {@HystrixProperty(name = "coreSize", value = "10"), @HystrixProperty(name = "maxQueueSize", value = "1000")}
    )
    public class AccountsDao {
    
      @Autowired
      private GenericRest rest;
    
      @Value("${user.service.name}")
      private String userServiceName;
    
      /**
       * <pre>获取用户列表</pre>
       *
       * @param query 用户信息
       * @return
       */
      @HystrixCommand
      public List<UserDO> getAccountList(UserDO query) {
        return RestHelper.exec(() -> {
          String url = RestHelper.formatUrl(userServiceName, "/user/getList");
          ResponseEntity<RestResponse<List<UserDO>>> responseEntity = rest.post(url, query,
                  new ParameterizedTypeReference<RestResponse<List<UserDO>>>() {
                  });
          return responseEntity.getBody();
        }).getResult();
      }
    
      /**
       * <pre>添加用户</pre>
       *
       * @param account 注册用户信息
       * @return
       */
      @HystrixCommand
      public int addAccount(UserDO account) {
        String result = RestHelper.exec(() -> {
          String url = RestHelper.formatUrl(userServiceName, "/user/register");
          ResponseEntity<RestResponse<String>> responseEntity = rest.post(url, account,
                  new ParameterizedTypeReference<RestResponse<String>>() {
                  });
          return responseEntity.getBody();
        }).getResult();
        log.info("Register Accounts Result Is {}", result);
        return 1;
      }
    
      /**
       * <pre>用户授权</pre>
       *
       * @param user 用户授权
       * @return
       */
      @HystrixCommand
      public UserDO auth(UserDO user) {
        return RestHelper.exec(() -> {
          String url = RestHelper.formatUrl(userServiceName, "/user/auth");
          ResponseEntity<RestResponse<UserDO>> responseEntity = rest.post(url, user,
                  new ParameterizedTypeReference<RestResponse<UserDO>>() {
                  });
          return responseEntity.getBody();
        }).getResult();
      }
    
      /**
       * <pre>激活用户</pre>
       *
       * @param key 激活码
       * @return
       */
      @HystrixCommand
      public boolean activateAccount(String key) {
        String result = RestHelper.exec(() -> {
          String url = RestHelper.formatUrl(userServiceName, "/user/activate?key=" + key);
          ResponseEntity<RestResponse<String>> responseEntity = rest.get(url,
                  new ParameterizedTypeReference<RestResponse<String>>() {
                  });
          return responseEntity.getBody();
        }).getResult();
        log.info("User Activate Result = {}", result);
        return true;
      }
    
      /**
       * <pre>注销登录</pre>
       *
       * @param token
       */
      @HystrixCommand
      public void logout(String token) {
        String result = RestHelper.exec(() -> {
          String url = RestHelper.formatUrl(userServiceName, "/user/logout?token=" + token);
          ResponseEntity<RestResponse<String>> responseEntity = rest.get(url,
                  new ParameterizedTypeReference<RestResponse<String>>() {
                  });
          return responseEntity.getBody();
        }).getResult();
        log.info("User Activate Result = {}", result);
      }
    
      /**
       * <pre>发送重置通知</pre>
       *
       * @param email     邮箱
       * @param notifyUrl 通知地址
       */
      @HystrixCommand
      public void resetNotify(String email, String notifyUrl) {
        RestHelper.exec(() -> {
          String url = RestHelper.formatUrl(userServiceName, "/user/resetNotify?email=" + email + "&notifyUrl=" + notifyUrl);
          ResponseEntity<RestResponse<String>> responseEntity = rest.get(url,
                  new ParameterizedTypeReference<RestResponse<String>>() {
                  });
          return responseEntity.getBody();
        });
      }
    
      /**
       * <pre>获取重置的邮箱</pre>
       *
       * @param key
       * @return
       */
      @HystrixCommand
      public String getResetEmail(String key) {
        return RestHelper.exec(() -> {
          String url = RestHelper.formatUrl(userServiceName, "/user/getEmailByKey?key=" + key);
          ResponseEntity<RestResponse<String>> responseEntity = rest.get(url,
                  new ParameterizedTypeReference<RestResponse<String>>() {
                  });
          return responseEntity.getBody();
        }).getResult();
      }
    
      /**
       * <pre>重置密码</pre>
       *
       * @param key    获取邮件的key
       * @param passwd 重置之后的密码
       * @return
       */
      @HystrixCommand
      public UserDO resetPassword(String key, String passwd) {
        return RestHelper.exec(() -> {
          String url = RestHelper.formatUrl(userServiceName, "/user/resetPwd?key=" + key + "&password=" + passwd);
          ResponseEntity<RestResponse<UserDO>> responseEntity = rest.get(url,
                  new ParameterizedTypeReference<RestResponse<UserDO>>() {
                  });
          return responseEntity.getBody();
        }).getResult();
      }
    
      /**
       * <pre>更新用户信息</pre>
       *
       * @param user
       * @return
       */
      @HystrixCommand
      public UserDO updateUserInfo(UserDO user) {
        return RestHelper.exec(() -> {
          String url = RestHelper.formatUrl(userServiceName, "/user/updateInfo");
          ResponseEntity<RestResponse<UserDO>> responseEntity = rest.post(url, user,
                  new ParameterizedTypeReference<RestResponse<UserDO>>() {
                  });
          return responseEntity.getBody();
        }).getResult();
      }
    
      /**
       * <pre>通过token查询账号信息</pre>
       *
       * @param token
       * @return
       */
      @HystrixCommand(fallbackMethod = "queryAccountByTokenFB")
      public UserDO queryAccountByToken(String token) {
        return RestHelper.exec(() -> {
          String url = RestHelper.formatUrl(userServiceName, "/user/getUserByToken?token=" + token);
          ResponseEntity<RestResponse<UserDO>> responseEntity = rest.get(url,
                  new ParameterizedTypeReference<RestResponse<UserDO>>() {
                  });
          return responseEntity.getBody();
        }).getResult();
      }
    
      /**
       * <pre>降级方法</pre>
       *
       * @param token
       * @return
       */
      public UserDO queryAccountByTokenFB(String token) {
        return new UserDO();
      }
    }
    
    
    ```

* 在请求最频繁的接口上添加降级方法
![添加降级方法](./photos/29.添加降级方法.png)

* 用户服务[House-User-Service]模拟延时
![用户服务模拟延时](./photos/32.用户服务模拟延时.png)

* 分别启动应用
    * House-User-Service
    ```bash
    mvn spring-boot:run
    ```
    
    * House-Room-Service
    ```bash
    mvn spring-boot:run
    ```
    
    * House-API-Gateway
    ```bash
    mvn spring-boot:run
    ```
* 批量执行登录请求脚本

    * 编写循环脚本（模拟登陆接口）
    ```bash
    #!/usr/bin/env bash
    for((i=1;i<1000;i++));
     do
        curl 'http://127.0.0.1:8080/accounts/signin' 
        -H 'Pragma: no-cache' 
        -H 'Origin: http://127.0.0.1:8080' 
        -H 'Accept-Encoding: gzip, deflate, br' 
        -H 'Accept-Language: zh-CN,zh;q=0.9' 
        -H 'Upgrade-Insecure-Requests: 1' 
        -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36' -H 'Content-Type: application/x-www-form-urlencoded' 
        -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8' 
        -H 'Cache-Control: no-cache' -H 'Referer: http://127.0.0.1:8080/accounts/signin' 
        -H 'Cookie: JSESSIONID=FAF58A4CAD6812AC2AA98212BE986E5D; token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJob3VzZS11c2VyIiwibmFtZSI6Ikpob25yYWluIiwiZXhwIjoxNTIyMDM0NDIxLCJlbWFpbCI6ImppYW5neTE5QDEyNi5jb20iLCJ0cyI6IjE1MjE5NDgwMjEifQ.oYTEqbgLx3qNtsJ6pfnLTlFLeU9XnSmHBLYachvK7go' 
        -H 'Connection: keep-alive' --data 'email=jiangy19%40126.com&password=jjyy1321&target=' --compressed >/tmp/api-log.txt
     done
    ```
    * 执行命令
    ```bash
    sh -x ./house-api-curl.sh
    ```
    * 查看线程
    ```bash
    jsp
    jstack xxx | grep userDao
    ```
    
* 演示结果截图
    * API-Gateway服务报错(登出操作异常)
    ![登出操作失败--超时](./photos/25.登出操作失败--超时.png)
    ![登出操作异常](./photos/26.登出操作异常.png)
    * 线程查看结果
    ![执行JVM的线程查看截图](./photos/30.执行JVM的线程查看截图.png)
    
* 模拟去掉降级方法
    * AccountsDao修改
    ![去掉降级方法演示](./photos/31.去掉降级方法演示.png)
    * 再次启动API-Gateway和运行脚本执行请求
    * 查看控制台日志
    ![断路器打开日志记录](./photos/33.断路器打开日志记录.png)
    
* 添加HystrixDashboard项目
    * 新建项目
    ![添加Hystrix-Dashboard项目01](./photos/34.添加Hystrix-Dashboard项目01.png)
    * 添加项目依赖
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-hystrix</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-hystrix-dashboard</artifactId>
    </dependency>
    ```
    * 配置文件（添加端口）和应用主类开启HystrixDashboard注解
    ![开启HystrixDashboard注解](./photos/34.开启HystrixDashboard注解.png)
    * 启动项目访问
    ![访问Hystrix项目](./photos/36.访问Hystrix项目.png)
    * 开启对House-API-Gateway项目监控
    ![添加Api-gateweay项目报错原因](./photos/36.添加Api-gateweay项目报错原因.png)
    
    ![请求一个接口查看结果](./photos/37.请求一个接口查看结果.png)
    * 启动脚本，查看监控面板，观察断路器打开
    ![启动脚本后断路器过一段时间就自动打开了](./photos/38.启动脚本后断路器过一段时间就自动打开了.png)
    * 添加降级方法，重新执行脚本
    ![加上降级方法再次执行脚本](./photos/39.加上降级方法再次执行脚本.png)
    * 断路器恢复的时间窗口
    ![断路器时间窗口恢复](./photos/40.断路器时间窗口恢复.png)
    * 正常请求激活断路器关闭
    ![恢复正常了](./photos/41.恢复正常了.png)
 
* 更新日期：2018-03-25