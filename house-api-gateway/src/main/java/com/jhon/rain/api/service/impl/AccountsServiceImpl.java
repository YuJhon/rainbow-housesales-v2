package com.jhon.rain.api.service.impl;

import com.google.common.collect.Lists;
import com.jhon.rain.api.dao.AccountsDao;
import com.jhon.rain.api.model.UserDO;
import com.jhon.rain.api.service.AccountsService;
import com.jhon.rain.api.service.FileService;
import com.jhon.rain.api.util.RestHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>功能描述</br>账号体系业务逻辑接口实现</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/23 10:13
 */
@Service
public class AccountsServiceImpl implements AccountsService {

  @Value("${domain.name}")
  private String domainName;

  @Autowired
  private AccountsDao accountsDao;

  @Autowired
  private FileService fileService;

  @Override
  public int addAccount(UserDO account) {
    if (account.getAvatarFile() != null) {
      List<String> imgs = fileService.getImgPaths(Lists.newArrayList(account.getAvatarFile()));
      account.setAvatar(imgs.get(0));
    }
    account.setEnableUrl(RestHelper.formatUrl(domainName, "/user/activate"));
    return accountsDao.addAccount(account);
  }

  @Override
  public boolean isExist(String email) {
    return getUserByEmail(email) != null;
  }

  /**
   * <pre>通过邮件获取用户</pre>
   *
   * @param email 用户邮箱
   * @return 用户信息
   */
  private UserDO getUserByEmail(String email) {
    UserDO queryInfo = new UserDO();
    queryInfo.setEmail(email);
    List<UserDO> userList = getUserByQuery(queryInfo);
    if (userList != null && !userList.isEmpty()) {
      return userList.get(0);
    }
    return null;
  }

  @Override
  public boolean enable(String key) {
    if (StringUtils.isBlank(key)){
      return false;
    }
    return accountsDao.activateAccount(key);
  }

  @Override
  public UserDO auth(String email, String password) {
    if (StringUtils.isBlank(email) || StringUtils.isBlank(password)) {
      return null;
    }
    UserDO user = new UserDO();
    user.setEmail(email);
    user.setPasswd(password);
    try {
      user = accountsDao.auth(user);
    } catch (Exception e) {
      return null;
    }
    return user;
  }

  @Override
  public String getResetEmail(String key) {
    return null;
  }

  @Override
  public UserDO reset(String key, String passwd) {
    return null;
  }

  @Override
  public void resetNotify(String email) {

  }

  @Override
  public int updateUser(UserDO user, String email) {
    return 0;
  }

  @Override
  public List<UserDO> getUserByQuery(UserDO user) {
    return accountsDao.getAccountList(user);
  }

  @Override
  public void logout(String token) {
    accountsDao.logout(token);
  }
}
