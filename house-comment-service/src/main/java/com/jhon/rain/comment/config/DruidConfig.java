package com.jhon.rain.comment.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * <p>功能描述</br>数据库配置</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/20 23:41
 */
@Configuration
public class DruidConfig {

  @ConfigurationProperties(prefix = "spring.druid")
  @Bean(initMethod = "init", destroyMethod = "close")
  public DruidDataSource dataSource(Filter statFilter) {
    DruidDataSource dataSource = new DruidDataSource();
    dataSource.setProxyFilters(Lists.newArrayList(statFilter));
    return dataSource;
  }

  @Bean
  public Filter statFilter() {
    StatFilter filter = new StatFilter();
    filter.setSlowSqlMillis(5000);
    filter.setLogSlowSql(true);
    filter.setMergeSql(true);
    return filter;
  }

  @Bean
  public ServletRegistrationBean servletRegistrationBean() {
    return new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
  }
}
