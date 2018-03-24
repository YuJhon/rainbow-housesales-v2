package com.jhon.rain.api.dao;

import com.jhon.rain.api.common.RestResponse;
import com.jhon.rain.api.config.GenericRest;
import com.jhon.rain.api.model.BlogDO;
import com.jhon.rain.api.model.UserDO;
import com.jhon.rain.api.model.dto.PageListResponse;
import com.jhon.rain.api.model.vo.BlogQueryReq;
import com.jhon.rain.api.util.RestHelper;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

/**
 * <p>功能描述</br>博客接口的数据访问层</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 20:27
 */
@Repository
public class BlogDao {

  @Autowired
  private GenericRest rest;

  @Value("${comment.service.name}")
  private String commentServiceName;

  /**
   * <pre>分页查询博客信息</pre>
   *
   * @param query
   * @param pageNum
   * @param pageSize
   * @return
   */
  public PageListResponse<BlogDO> queryBlogs(BlogDO query, Integer pageNum, Integer pageSize) {
    BlogQueryReq blogQueryReq = new BlogQueryReq();
    blogQueryReq.setBlog(query);
    blogQueryReq.setPageNum(pageNum);
    blogQueryReq.setPageSize(pageSize);

    return RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(commentServiceName, "/blog/list");
      ResponseEntity<RestResponse<PageListResponse<BlogDO>>> responseEntity = rest.post(url, blogQueryReq,
              new ParameterizedTypeReference<RestResponse<PageListResponse<BlogDO>>>() {
              });
      return responseEntity.getBody();
    }).getResult();

  }

  /**
   * <pre>通过主键查询博客信息</pre>
   *
   * @param id
   * @return
   */
  public BlogDO queryBlogById(Integer id) {
    return RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(commentServiceName, "/blog/queryById?id=" + id);
      ResponseEntity<RestResponse<BlogDO>> responseEntity = rest.get(url,
              new ParameterizedTypeReference<RestResponse<BlogDO>>() {
              });
      return responseEntity.getBody();
    }).getResult();
  }
}
