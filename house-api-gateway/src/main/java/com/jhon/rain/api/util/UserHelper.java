package com.jhon.rain.api.util;

import com.jhon.rain.api.common.ResultMsg;
import com.jhon.rain.api.model.UserDO;
import org.apache.commons.lang.StringUtils;

import java.util.Objects;

/**
 * <p>功能描述</br>辅助类</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/13 8:48
 */
public class UserHelper {

  public static ResultMsg validate(UserDO account) {
    /** 用户名 **/
    if (StringUtils.isBlank(account.getName())) {
      return ResultMsg.errorMsg("用户名不能为空！");
    }
    /** 邮箱 **/
    if (StringUtils.isBlank(account.getEmail())) {
      return ResultMsg.errorMsg("Email 不能为空！");
    }
    /** 密码的校验 **/
    if (StringUtils.isBlank(account.getPasswd()) || StringUtils.isBlank(account.getConfirmPasswd())
            || !account.getPasswd().equals(account.getConfirmPasswd())) {
      return ResultMsg.errorMsg("密码输入有误！");
    }
    /** 密码的长度 **/
    if (account.getPasswd().length() < 6) {
      return ResultMsg.errorMsg("密码不能少于6位！");
    }

    return ResultMsg.successMsg("");
  }

  /**
   * <pre>重置密码的密码的校验</pre>
   *
   * @param key             校验的辅助参数
   * @param password        密码
   * @param confirmPassword 确认密码
   * @return
   */
  public static ResultMsg validateResetPassword(String key, String password, String confirmPassword) {
    if (StringUtils.isBlank(key) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)) {
      return ResultMsg.errorMsg("参数有误！");
    }
    if (!Objects.equals(password, confirmPassword)) {
      return ResultMsg.errorMsg("密码必须和确认密码保持一致！");
    }
    return ResultMsg.successMsg("");
  }
}
