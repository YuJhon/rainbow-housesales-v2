package com.jhon.rain.api.service.impl;

import com.jhon.rain.api.dao.CommentDao;
import com.jhon.rain.api.model.CommentDO;
import com.jhon.rain.api.model.vo.CommentReq;
import com.jhon.rain.api.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>功能描述</br>评论接口业务逻辑实现</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 15:14
 */
@Service
public class CommentServiceImpl implements CommentService {

  @Autowired
  private CommentDao commentDao;

  @Override
  public List<CommentDO> getHouseComments(Long houseId, int size) {
    CommentReq commentDto = new CommentReq();
    commentDto.setHouseId(houseId);
    commentDto.setSize(size);
    commentDto.setType(1);
    return commentDao.queryCommentList(commentDto);
  }

  @Override
  public List<CommentDO> getBlogComments(Integer blogId) {
    CommentReq commentReq = new CommentReq();
    commentReq.setBlogId(blogId);
    commentReq.setSize(8);
    commentReq.setType(2);
    return commentDao.queryCommentList(commentReq);
  }

  @Override
  public int addHouseComment(Long houseId, String content, Long userId) {
    CommentReq commentReq = new CommentReq();
    commentReq.setHouseId(houseId);
    commentReq.setContent(content);
    commentReq.setUserId(userId);
    commentReq.setType(1);
    return commentDao.addComment(commentReq);
  }

  @Override
  public int addBlogComment(Integer blogId, String content, Long userId) {
    CommentReq commentReq = new CommentReq();
    commentReq.setBlogId(blogId);
    commentReq.setContent(content);
    commentReq.setUserId(userId);
    commentReq.setType(2);
    return commentDao.addComment(commentReq);
  }
}
