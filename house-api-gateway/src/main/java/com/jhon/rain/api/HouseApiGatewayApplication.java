package com.jhon.rain.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@Controller
public class HouseApiGatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(HouseApiGatewayApplication.class, args);
  }

  @Autowired
  private DiscoveryClient discoveryClient;

  /**
   * <pre>获取用户实例</pre>
   *
   * @return
   */
  @RequestMapping("index1")
  @ResponseBody
  public List<ServiceInstance> getRegister() {
    return discoveryClient.getInstances("house-user-service");
  }
}
