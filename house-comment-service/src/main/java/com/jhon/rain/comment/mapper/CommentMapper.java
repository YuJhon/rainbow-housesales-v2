package com.jhon.rain.comment.mapper;

import com.jhon.rain.comment.model.CommentDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>功能描述</br>评论数据访问层接口定义</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 19:18
 */
@Mapper
public interface CommentMapper {
  /**
   * <pre>查询列表</pre>
   *
   * @param houseId 房产Id
   * @param size    条数
   * @return
   */
  List<CommentDO> queryHouseComments(@Param("houseId") Long houseId, @Param("size") Integer size);

  /**
   * <pre>查询列表</pre>
   *
   * @param blogId 博客Id
   * @param size   条数
   * @return
   */
  List<CommentDO> queryBlogComments(@Param("blogId") Integer blogId, @Param("size") Integer size);

  /**
   * <pre>插入记录</pre>
   *
   * @param comment 评论
   * @return
   */
  Integer insert(CommentDO comment);
}
