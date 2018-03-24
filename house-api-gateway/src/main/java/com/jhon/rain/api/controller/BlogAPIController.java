package com.jhon.rain.api.controller;

import com.jhon.rain.api.common.CommonConstants;
import com.jhon.rain.api.model.BlogDO;
import com.jhon.rain.api.model.CommentDO;
import com.jhon.rain.api.model.HouseDO;
import com.jhon.rain.api.page.PageData;
import com.jhon.rain.api.service.BlogService;
import com.jhon.rain.api.service.CommentService;
import com.jhon.rain.api.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * <p>功能描述</br>博客接口控制器</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 20:24
 */
@Controller
@RequestMapping("blog")
public class BlogAPIController {

  @Autowired
  private CommentService commentService;

  @Autowired
  private HouseService houseService;

  @Autowired
  private BlogService blogService;


  /**
   * <pre>查询博客列表</pre>
   *
   * @param pageSize
   * @param pageNum
   * @param query
   * @param modelMap
   * @return
   */
  @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
  public String list(Integer pageSize, Integer pageNum, BlogDO query, ModelMap modelMap) {
    PageData<BlogDO> ps = blogService.queryBlogs(query, pageSize, pageNum);
    List<HouseDO> houses = houseService.getHotHouses(CommonConstants.RECOM_SIZE);
    modelMap.put("recomHouses", houses);
    modelMap.put("ps", ps);
    return "/blog/listing";
  }

  /**
   * <pre>查询博客详情</pre>
   *
   * @param id
   * @param modelMap
   * @return
   */
  @RequestMapping(value = "/detail", method = {RequestMethod.POST, RequestMethod.GET})
  public String blogDetail(int id, ModelMap modelMap) {
    BlogDO blog = blogService.queryBlogById(id);
    List<CommentDO> comments = commentService.getBlogComments(id);
    List<HouseDO> houses = houseService.getHotHouses(CommonConstants.RECOM_SIZE);
    modelMap.put("recomHouses", houses);
    modelMap.put("blog", blog);
    modelMap.put("commentList", comments);
    return "/blog/detail";
  }
}
