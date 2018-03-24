package com.jhon.rain.api.controller;

import com.jhon.rain.api.interceptor.UserContext;
import com.jhon.rain.api.model.UserDO;
import com.jhon.rain.api.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>功能描述</br>评论控制器</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 20:25
 */
@Controller
@RequestMapping("comment")
public class CommentAPIController {

  @Autowired
  private CommentService commentService;

  /**
   * <pre>添加评论</pre>
   *
   * @param content 内容
   * @param houseId 房产Id
   * @param modelMap 参数
   * @return
   */
  @RequestMapping(value = "/leaveComment")
  public String leaveComment(String content, Long houseId, ModelMap modelMap) {
    UserDO user = UserContext.getUser();
    Long userId = user.getId();
    commentService.addHouseComment(houseId, content, userId);
    return "redirect:/house/detail?id=" + houseId;
  }

  /**
   * <pre>添加博客评论</pre>
   *
   * @param content 内容
   * @param blogId 博客Id
   * @param modelMap 参数
   * @return
   */
  @RequestMapping(value = "/leaveBlogComment")
  public String leaveBlogComment(String content, Integer blogId, ModelMap modelMap) {
    UserDO user = UserContext.getUser();
    Long userId = user.getId();
    commentService.addBlogComment(blogId, content, userId);
    return "redirect:/blog/detail?id=" + blogId;
  }
}
