package com.jhon.rain.comment.controller;

import com.jhon.rain.comment.common.RestResponse;
import com.jhon.rain.comment.model.BlogDO;
import com.jhon.rain.comment.model.dto.PageListResponse;
import com.jhon.rain.comment.model.vo.BlogQueryReq;
import com.jhon.rain.comment.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>功能描述</br>博客控制器</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 19:15
 */
@RestController
@RequestMapping("blog")
public class BlogController {

  @Autowired
  private BlogService blogService;

  /**
   * <pre>分页查询列表</pre>
   *
   * @param queryReq
   * @return
   */
  @PostMapping("list")
  public RestResponse<PageListResponse<BlogDO>> list(@RequestBody BlogQueryReq queryReq) {
    BlogDO query = queryReq.getBlog();
    Integer pageNum = queryReq.getPageNum();
    Integer pageSize = queryReq.getPageSize();
    PageListResponse<BlogDO> pageListResp = blogService.queryBlogList(query,pageNum, pageSize);
    return RestResponse.success(pageListResp);
  }

  /**
   * <pre>通过主键查询博客</pre>
   *
   * @param id
   * @return
   */
  @GetMapping("queryById")
  public RestResponse<BlogDO> queryRecord(@RequestParam(name = "id") Integer id) {
    BlogDO blog = blogService.queryOneBlogById(id);
    return RestResponse.success(blog);
  }
}
