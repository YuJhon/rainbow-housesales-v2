package com.jhon.rain.comment.mapper;

import com.jhon.rain.comment.model.BlogDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 19:18
 */
@Mapper
public interface BlogMapper {
  /**
   * <pre>查询博客列表</pre>
   *
   * @param query
   * @return
   */
  List<BlogDO> queryBlogList(BlogDO query);

  /**
   * <pre>通过主键查询记录</pre>
   *
   * @param id
   * @return
   */
  BlogDO queryRecordById(Integer id);
}
