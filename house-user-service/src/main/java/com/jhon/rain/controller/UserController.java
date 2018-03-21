package com.jhon.rain.controller;

import com.jhon.rain.common.RestResponse;
import com.jhon.rain.exception.NodeItemException;
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
    if (id == null){
      throw new NodeItemException(NodeItemException.CustomType.WRONG_PAGE,"页面不存在");
    }
    return RestResponse.success("test-name");
  }
}
