package com.jhon.rain.api.interceptor;

import com.jhon.rain.api.model.UserDO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/14 20:05
 */
@Component
public class AuthActionInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o) throws Exception {
    /** 获取当前操作人 **/
    UserDO user = UserContext.getUser();
    if (user == null) {
      String msg = URLEncoder.encode("请先登陆", "utf-8");
      String target = URLEncoder.encode(httpServletRequest.getRequestURL().toString(), "utf-8");
      if ("GET".equalsIgnoreCase(httpServletRequest.getMethod())) {
        httpServletResponse.sendRedirect("/accounts/signin?errorMsg=" + msg + "&target=" + target);
        return false;
      } else {
        httpServletResponse.sendRedirect("/accounts/signin?errorMsg=" + msg);
        return false;
      }
    }
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         Object o, ModelAndView modelAndView) throws Exception {

  }

  @Override
  public void afterCompletion(HttpServletRequest httpServletRequest,
                              HttpServletResponse httpServletResponse,
                              Object o, Exception e) throws Exception {

  }
}
