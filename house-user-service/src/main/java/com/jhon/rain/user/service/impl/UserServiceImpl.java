package com.jhon.rain.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.jhon.rain.user.common.Constants;
import com.jhon.rain.user.exception.UserException;
import com.jhon.rain.user.helper.MailSenderHelper;
import com.jhon.rain.user.mapper.UserMapper;
import com.jhon.rain.user.model.UserDO;
import com.jhon.rain.user.service.UserService;
import com.jhon.rain.user.utils.BeanHelper;
import com.jhon.rain.user.utils.HashUtils;
import com.jhon.rain.user.utils.JWTHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>功能描述</br>用户服务业务逻辑接口实现类</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/21 21:50
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  @Value("${file.prefix}")
  private String imgPrefix;

  @Value("${project.name.info}")
  private String projectName;

  @Autowired
  private MailSenderHelper mailSenderHelper;


  @Override
  public UserDO getUserById(Long id) {
    UserDO userInfo = null;
    String redisUserKey = Constants.REDIS_USER_FLAG_KEY + id;
    /** 首先从缓存中获取 **/
    String userJsonStr = stringRedisTemplate.opsForValue().get(redisUserKey);
    /** 判断是否为空 **/
    if (Strings.isNullOrEmpty(userJsonStr)) {
      /** 从数据库获取 **/
      userInfo = userMapper.queryUserById(id);
      if (userInfo != null) {
        userInfo.setAvatar(imgPrefix + userInfo.getAvatar());
        /** 缓存设置时间为5分钟 **/
        stringRedisTemplate.opsForValue().set(redisUserKey, JSON.toJSONString(userInfo), 5, TimeUnit.MINUTES);
      } else {
        log.error("Query User Info By Id={},Record Is Not Exist!", id);
      }
    } else {
      userInfo = JSON.parseObject(userJsonStr, UserDO.class);
    }
    return userInfo;
  }


  @Override
  public List<UserDO> getUserList(UserDO user) {
    List<UserDO> userList = userMapper.query(user);
    /** 设置头像信息 **/
    userList.forEach(userItem -> {
      userItem.setAvatar(imgPrefix + userItem.getAvatar());
    });
    return userList;
  }

  @Override
  public int registerAccount(UserDO account, String enableUrl) {
    int influenceRecord = 0;
    /** 密码加密 **/
    account.setPasswd(HashUtils.encryPassword(account.getPasswd()));
    BeanHelper.onInsert(account);
    account.setEnable(0);
    influenceRecord = userMapper.insert(account);
    /** 邮件通知 **/
    registerNotify(account.getEmail(), enableUrl);
    return influenceRecord;
  }

  @Override
  public int activateAccount(String key) {
    String email = stringRedisTemplate.opsForValue().get(key);
    if (StringUtils.isBlank(email)) {
      /** 替换成用户模块自定义的异常 **/
      throw new RuntimeException("Invalidate Activation Key");
    }
    UserDO user = new UserDO();
    user.setEmail(email);
    user.setEnable(1);
    return userMapper.update(user);
  }

  @Override
  public UserDO auth(String email, String password) {
    if (StringUtils.isBlank(email) || StringUtils.isBlank(password)) {
      throw new UserException(UserException.CustomType.USER_AUTH_FAIL, "User Authentication Failed!");
    }
    UserDO queryUser = new UserDO();
    queryUser.setEmail(email);
    queryUser.setPasswd(HashUtils.encryPassword(password));
    queryUser.setEnable(1);
    List<UserDO> userList = userMapper.query(queryUser);
    if (userList != null && !userList.isEmpty()) {
      UserDO retUser = userList.get(0);
      /** token处理 **/
      retUser = onLoginProcess(retUser);
      return retUser;
    } else {
      log.error("User(Email={}) Query With Password Is Not Exist!", email);
      throw new UserException(UserException.CustomType.USER_AUTH_FAIL, "User Authentication Failed!");
    }
  }

  @Override
  public UserDO getLoginedUserByToken(String token) {
    Map<String, String> jwtMapInfo = Maps.newHashMap();
    try {
      jwtMapInfo = JWTHelper.verifyToken(token);
    } catch (Exception e) {
      throw new UserException(UserException.CustomType.USER_NOT_LOGIN, "Current User Is Not Login!");
    }
    /** 获取用户的邮箱 **/
    String email = jwtMapInfo.get("email");
    /** 判断是否已经过期 **/
    Long expired = stringRedisTemplate.getExpire(email);
    if (expired <= 0L) {
      throw new UserException(UserException.CustomType.USER_NOT_LOGIN, "Current User Is Not Login!");
    }
    /** 刷新Token **/
    refreshToken(email, token);
    /** 查询用户 **/
    UserDO user = userMapper.queryUserByEmail(email);
    user.setToken(token);
    user.setAvatar(imgPrefix+user.getAvatar());
    return user;
  }

  @Override
  public void invalidate(String token) {
    /** 校验token的有效性 **/
    Map<String, String> jwtMap = JWTHelper.verifyToken(token);
    /** 从缓存中移除该用户的token信息 **/
    stringRedisTemplate.delete(jwtMap.get(Constants.USER_EMAIL_FLAG));
  }

  @Override
  public UserDO updateUserInfo(UserDO userInfo) {
    /** 1.判断用户的唯一标识是否存在 **/
    String email = userInfo.getEmail();
    if (StringUtils.isBlank(email)) {
      return null;
    }
    if (StringUtils.isNotBlank(userInfo.getPasswd())) {
      userInfo.setPasswd(HashUtils.encryPassword(userInfo.getPasswd()));
    }
    /** 更新操作 **/
    userMapper.update(userInfo);
    /** 查询用户信息 [TODO 事务的问题]**/
    return userMapper.queryUserByEmail(email);
  }

  @Override
  public UserDO resetPassword(String key, String password) {
    String email = getEmailByKey(key);
    UserDO updateUser = new UserDO();
    updateUser.setEmail(email);
    updateUser.setPasswd(HashUtils.encryPassword(password));
    userMapper.update(updateUser);
    return getUserByEmail(email);
  }

  /**
   * <pre>通过邮箱查询用户信息</pre>
   *
   * @param email 邮箱
   * @return
   */
  private UserDO getUserByEmail(String email) {
    UserDO queryInfo = new UserDO();
    queryInfo.setEmail(email);
    List<UserDO> userList = getUserList(queryInfo);
    if (userList == null || userList.isEmpty()) {
      throw new UserException(UserException.CustomType.USER_NOT_FOUND, "User Is Not Exist!");
    }
    return userList.get(0);
  }


  @Override
  public String getEmailByKey(String key) {
    return stringRedisTemplate.opsForValue().get(key);
  }

  @Override
  public void resetEmailNotify(String email, String notifyUrl) {
    String randomKey = "reset_" + RandomStringUtils.randomAlphabetic(10);
    stringRedisTemplate.opsForValue().set(randomKey, email);
    stringRedisTemplate.expire(randomKey, 1, TimeUnit.HOURS);
    String subject = projectName + "重置密码链接";
    String content = notifyUrl + "?key=" + randomKey;
    mailSenderHelper.sendSimpleCustomEmail(subject, content, email);
  }

  /**
   * <pre>登录成功的处理</pre>
   *
   * @param loginUser 当前需要鉴权的用户
   */
  private UserDO onLoginProcess(UserDO loginUser) {
    /** 生成Token **/
    String token = JWTHelper.generateToken(ImmutableMap.of(Constants.USER_EMAIL_FLAG, loginUser.getEmail(),
            Constants.USER_NAME_FLAG, loginUser.getName(),
            Constants.TIMESTAMP, Instant.now().getEpochSecond() + ""));
    /** Token缓存到Redis中,并且刷新token **/
    refreshToken(loginUser.getEmail(), token);
    /** 将新的token设置到用户对象上 **/
    loginUser.setToken(token);
    return loginUser;
  }

  /**
   * <pre>刷新token</pre>
   *
   * @param email 用户唯一标识
   * @param token 重新生成的token
   */
  private String refreshToken(String email, String token) {
    stringRedisTemplate.opsForValue().set(email, token);
    /** 设置30分钟的过期时间 **/
    stringRedisTemplate.expire(email, 30, TimeUnit.MINUTES);
    return token;
  }


  /**
   * <pre>邮件通知</pre>
   *
   * @param email     用户注册的邮箱
   * @param enableUrl 激活的链接
   */
  private void registerNotify(String email, String enableUrl) {
    String randomKey = HashUtils.hashString(email) + RandomStringUtils.randomAlphabetic(10);
    /** 随机码放入缓存中 **/
    stringRedisTemplate.opsForValue().set(randomKey, email, 1, TimeUnit.HOURS);
    String title = projectName + "注册激活通知";
    String url = enableUrl + "?key=" + randomKey;
    String prefix = "你好，<br>感谢你注册房产网。<br>";
    String suffix = "请点击以下链接(1小时内有效)激活账号。";
    mailSenderHelper.sendHtmlRegistionNotifyMail(title, url, prefix, suffix, email);
  }
}
