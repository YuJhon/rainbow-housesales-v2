package com.jhon.rain.api.service;

import com.jhon.rain.api.model.BlogDO;
import com.jhon.rain.api.page.PageData;

/**
 * <p>功能描述</br>博客业务逻辑接口定义</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 20:26
 */
public interface BlogService {
  /**
   * <pre>分页查询博客</pre>
   *
   * @param query    查询条件
   * @param pageSize 每页显示条数
   * @param pageNum  当前页
   * @return
   */
  PageData<BlogDO> queryBlogs(BlogDO query, Integer pageSize, Integer pageNum);

  /**
   * <pre>查询博客详情</pre>
   *
   * @param id 博客Id
   * @return
   */
  BlogDO queryBlogById(Integer id);
}
