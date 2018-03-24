package com.jhon.rain.comment.service;

import com.jhon.rain.comment.model.CommentDO;

import java.util.List;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 19:17
 */
public interface CommentService {
  /**
   * <pre>添加房产评论</pre>
   *
   * @param houseId
   * @param userId
   * @param content
   * @return
   */
  int addHouseComment(Long houseId, Long userId, String content);

  /**
   * <pre>添加博客评论</pre>
   *
   * @param blogId
   * @param userId
   * @param content
   * @return
   */
  int addBlogComment(Integer blogId, Long userId, String content);

  /**
   * <pre>查找房产评论列表</pre>
   *
   * @param houseId
   * @param size
   * @return
   */
  List<CommentDO> getHouseComments(Long houseId, Integer size);

  /**
   * <pre>查找博客评论列表</pre>
   *
   * @param blogId
   * @param size
   * @return
   */
  List<CommentDO> getBlogComments(Integer blogId, Integer size);
}
