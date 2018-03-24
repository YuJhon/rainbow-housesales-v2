package com.jhon.rain.api.interceptor;

import com.google.common.base.Joiner;
import com.jhon.rain.api.dao.AccountsDao;
import com.jhon.rain.api.dao.UserDao;
import com.jhon.rain.api.model.UserDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>功能描述</br>拦截授权</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/14 20:05
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

  private static final String TOKEN_COOKIE = "token";

  @Autowired
  private AccountsDao accountsDao;

  @Override
  public boolean preHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o) throws Exception {
    Map<String, String[]> map = httpServletRequest.getParameterMap();
    map.forEach((k, v) -> {
      httpServletRequest.setAttribute(k, Joiner.on(",").join(v));
    });
    String requestUri = httpServletRequest.getRequestURI();
    if (requestUri.startsWith("/static") || requestUri.startsWith("/error")) {
      return true;
    }

    Cookie cookie = WebUtils.getCookie(httpServletRequest, TOKEN_COOKIE);
    if (cookie != null && StringUtils.isNoneBlank(cookie.getValue())) {
      UserDO user = accountsDao.queryAccountByToken(cookie.getValue());
      if (user != null) {
        /** 将当前登陆的用户信息放到线程局部变量中:方便在同一个线程中使用 **/
        UserContext.setUser(user);
      }
    }
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         Object o,
                         ModelAndView modelAndView) throws Exception {
    String requestURI = httpServletRequest.getRequestURI();
    if (requestURI.startsWith("/static") || requestURI.startsWith("/error")) {
      return;
    }
    UserDO user = UserContext.getUser();
    if (user != null && StringUtils.isNoneBlank(user.getToken())) {
      String token = requestURI.startsWith("logout") ? "" : user.getToken();
      Cookie cookie = new Cookie(TOKEN_COOKIE, token);
      cookie.setPath("/");
      cookie.setHttpOnly(false);
      httpServletResponse.addCookie(cookie);
    }
  }

  @Override
  public void afterCompletion(HttpServletRequest httpServletRequest,
                              HttpServletResponse httpServletResponse,
                              Object o, Exception e) throws Exception {
    /** 移除数据 **/
    UserContext.remove();
  }
}
