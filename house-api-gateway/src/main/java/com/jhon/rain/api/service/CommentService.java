package com.jhon.rain.api.service;

import com.jhon.rain.api.model.CommentDO;

import java.util.List;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 15:14
 */
public interface CommentService {
  /**
   * <pre>获取房产的评论信息</pre>
   *
   * @param houseId 房产id
   * @param size    查询条数
   * @return
   */
  List<CommentDO> getHouseComments(Long houseId, int size);

  /**
   * <pre>获取博客评论</pre>
   *
   * @param blogId 博客Id
   * @return
   */
  List<CommentDO> getBlogComments(Integer blogId);

  /**
   * <pre>添加房产评论</pre>
   *
   * @param houseId 房产Id
   * @param content 评论内容
   * @param userId  当前用户
   */
  int addHouseComment(Long houseId, String content, Long userId);

  /**
   * <pre>添加博客评论</pre>
   *
   * @param blogId  博客Id
   * @param content 评论内容
   * @param userId  当前用户
   * @return
   */
  int addBlogComment(Integer blogId, String content, Long userId);
}
