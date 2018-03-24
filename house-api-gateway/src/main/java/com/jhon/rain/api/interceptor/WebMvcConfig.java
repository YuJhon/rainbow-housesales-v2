package com.jhon.rain.api.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/14 20:07
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

  @Autowired
  private AuthInterceptor authInterceptor;

  @Autowired
  private AuthActionInterceptor authActionInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authInterceptor).excludePathPatterns("/static").addPathPatterns("/**");
    /** 拦截对应的请求 **/
    registry.addInterceptor(authActionInterceptor)
            .addPathPatterns("/accounts/profile")
            .addPathPatterns("/accounts/profileSubmit")
            .addPathPatterns("/house/bookmarked")
            .addPathPatterns("/house/del")
            .addPathPatterns("/house/ownlist")
            .addPathPatterns("/house/add")
            .addPathPatterns("/house/toAdd")
            .addPathPatterns("/agency/agentMsg")
            .addPathPatterns("/comment/leaveComment")
            .addPathPatterns("/comment/leaveBlogComment");
    super.addInterceptors(registry);
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOrigins("*")//放行哪些域名
            .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")//放行哪些http方法
            .allowCredentials(false)//是否允许发送cookie
            .allowedHeaders("*")//用于预检请求，放行哪些header
            .exposedHeaders("header1","header2")//暴露哪些头部信息
            .maxAge(3600);
  }
}
