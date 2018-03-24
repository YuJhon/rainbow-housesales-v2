package com.jhon.rain.comment.controller;

import com.google.common.collect.Lists;
import com.jhon.rain.comment.common.RestResponse;
import com.jhon.rain.comment.model.CommentDO;
import com.jhon.rain.comment.model.vo.CommentReq;
import com.jhon.rain.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * <p>功能描述</br>评论控制器</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 19:15
 */
@RestController
@RequestMapping("comment")
public class CommentController {

  @Autowired
  private CommentService commentService;

  /**
   * <pre>留言</pre>
   *
   * @param commentReq
   * @return
   */
  @PostMapping("add")
  public RestResponse<String> leaveComment(@RequestBody CommentReq commentReq) {
    Integer type = commentReq.getType();
    Long houseId = commentReq.getHouseId();
    Long userId = commentReq.getUserId();
    Integer blogId = commentReq.getBlogId();
    String content = commentReq.getContent();
    if (Objects.equals(1, type)) {
      commentService.addHouseComment(houseId, userId, content);
    } else {
      commentService.addBlogComment(blogId, userId, content);
    }
    return RestResponse.success();
  }

  /**
   * <pre>获取评论列表</pre>
   *
   * @param commentReq
   * @return
   */
  @PostMapping("list")
  public RestResponse<List<CommentDO>> list(@RequestBody CommentReq commentReq) {
    Integer type = commentReq.getType();
    Long houseId = commentReq.getHouseId();
    Integer blogId = commentReq.getBlogId();
    Integer size = commentReq.getSize();
    List<CommentDO> commentList = Lists.newArrayList();
    if (Objects.equals(1, type)) {
      commentList = commentService.getHouseComments(houseId,size);
    } else {
      commentList = commentService.getBlogComments(blogId,size);
    }
    return RestResponse.success(commentList);
  }
}
