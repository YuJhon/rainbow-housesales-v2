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
