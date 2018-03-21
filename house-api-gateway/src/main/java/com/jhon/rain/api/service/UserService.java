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
