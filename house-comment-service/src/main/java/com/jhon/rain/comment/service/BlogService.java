package com.jhon.rain.comment.service;

import com.jhon.rain.comment.model.BlogDO;
import com.jhon.rain.comment.model.dto.PageListResponse;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 19:16
 */
public interface BlogService {
  /**
   * <pre>分页查询记录</pre>
   *
   * @param query
   * @param pageNum
   * @param pageSize
   * @return
   */
  PageListResponse<BlogDO> queryBlogList(BlogDO query, Integer pageNum, Integer pageSize);

  /**
   * <pre>通过主键查询记录</pre>
   *
   * @param id
   * @return
   */
  BlogDO queryOneBlogById(Integer id);
}
