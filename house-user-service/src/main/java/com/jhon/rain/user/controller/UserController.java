package com.jhon.rain.user.controller;

import com.jhon.rain.user.common.RestResponse;
import com.jhon.rain.user.exception.NodeItemException;
import com.jhon.rain.user.model.UserDO;
import com.jhon.rain.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>功能描述</br>用户服务类暴露的接口</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/20 22:27
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

  /********************************************************************************************************************/
  /*                                        测试                                                                       */
  /********************************************************************************************************************/

  @Value("${server.port}")
  private Integer port;

  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  /**
   * <pre>测试微服务接口</pre>
   *
   * @param id
   * @return
   */
  @RequestMapping("getUsername")
  public RestResponse<String> getUserName(Long id) {
    log.info("In Coming Request!");
    if (id == null) {
      throw new NodeItemException(NodeItemException.CustomType.WRONG_PAGE, "页面不存在");
    }
    stringRedisTemplate.opsForValue().set("jhon", "rain");
    log.info(stringRedisTemplate.opsForValue().get("jhon"));
    return RestResponse.success("test-name");
  }

  /********************************************************************************************************************/
  /*                                                                                                                  */
  /********************************************************************************************************************/


  @Autowired
  private UserService userService;


  /*************************************************************查询***************************************************/
  /**
   * <pre>通过Id查询用户</pre>
   *
   * @param id
   * @return
   */
  @GetMapping("getById")
  public RestResponse<UserDO> getUserById(@RequestParam(name = "id") Long id) {
    /** 为空的判断 TODO **/
    UserDO user = userService.getUserById(id);
    return RestResponse.success(user);
  }

  /**
   * <pre>获取用户列表信息</pre>
   *
   * @param user
   * @return
   */
  @PostMapping("getList")
  public RestResponse<List<UserDO>> getUserList(@RequestBody UserDO user) {
    List<UserDO> userList = userService.getUserList(user);
    return RestResponse.success(userList);
  }

  /************************************************************注册*****************************************************/
  /**
   * <pre>注册</pre>
   *
   * @param account 用户信息
   * @return
   */
  @PostMapping("register")
  public RestResponse<String> register(@RequestBody UserDO account) {
    int influenceRecord = userService.registerAccount(account, account.getEnableUrl());
    return RestResponse.success(influenceRecord + "");
  }

  /**
   * <pre>激活用户</pre>
   *
   * @param key 激活码
   * @return
   */
  @GetMapping("activate")
  public RestResponse<String> activation(@RequestParam(name = "key", required = true) String key) {
    int influenceRecord = userService.activateAccount(key);
    return RestResponse.success(influenceRecord + "");
  }

  /************************************************************登录/鉴权*************************************************/
  /**
   * <pre>鉴权</pre>
   *
   * @param queryInfo 用户信息
   * @return 鉴权之后的用户信息
   */
  @PostMapping("auth")
  public RestResponse<UserDO> auth(@RequestBody UserDO queryInfo) {
    String email = queryInfo.getEmail();
    String password = queryInfo.getPasswd();
    UserDO user = userService.auth(email, password);
    return RestResponse.success(user);
  }

  /**
   * <pre>通过token来获取用户</pre>
   *
   * @param token 鉴权之后的Token
   * @return
   */
  @GetMapping("getByToken")
  public RestResponse<UserDO> getUserByToken(@RequestParam(name = "token", required = true) String token) {
    /** 模拟服务断路
    if(token != null){
      try {
        Thread.sleep(10000000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
     **/
    UserDO userInfo = userService.getLoginedUserByToken(token);
    return RestResponse.success(userInfo);
  }

  /**
   * <pre>登出</pre>
   *
   * @param token token信息
   * @return
   */
  @GetMapping("logout")
  public RestResponse<String> logout(@RequestParam(name = "token") String token) {
    /** 模拟服务断路
    if(token != null){
      try {
        Thread.sleep(10000000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
     **/
    userService.invalidate(token);
    return RestResponse.success("success");
  }

  /**
   * <pre>更新用户信息</pre>
   *
   * @param userInfo 用户信息
   * @return
   */
  @PostMapping("updateInfo")
  public RestResponse<UserDO> updateUserInfo(@RequestBody UserDO userInfo) {
    UserDO retUser = userService.updateUserInfo(userInfo);
    return RestResponse.success(retUser);
  }

  /**
   * <pre>重置用户密码</pre>
   *
   * @param key      重置密码的key
   * @param password 新密码
   * @return
   */
  @GetMapping("resetPwd")
  public RestResponse<UserDO> resetPassword(@RequestParam(name = "key") String key,
                                            @RequestParam(name = "password") String password) {
    UserDO retUser = userService.resetPassword(key, password);
    return RestResponse.success(retUser);
  }

  /**
   * <pre>通过key获取用户的邮箱</pre>
   * @param key
   * @return
   */
  @GetMapping("getEmailByKey")
  public RestResponse<String> getEmailByKey(@RequestParam(name = "key") String key) {
    String email = userService.getEmailByKey(key);
    return RestResponse.success(email);
  }

  /**
   * <pre>重置密码邮件通知</pre>
   *
   * @param email     用户邮箱
   * @param notifyUrl 通知的url
   * @return
   */
  @GetMapping("resetNotify")
  public RestResponse<String> resetNotify(@RequestParam(name = "email") String email,
                                          @RequestParam(name = "notifyUrl") String notifyUrl) {
    userService.resetEmailNotify(email,notifyUrl);
    return RestResponse.success();
  }
}
