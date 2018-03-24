package com.jhon.rain.api.service.impl;

import com.jhon.rain.api.dao.BlogDao;
import com.jhon.rain.api.model.BlogDO;
import com.jhon.rain.api.model.UserDO;
import com.jhon.rain.api.model.dto.PageListResponse;
import com.jhon.rain.api.model.vo.BlogQueryReq;
import com.jhon.rain.api.page.PageData;
import com.jhon.rain.api.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>功能描述</br>博客业务逻辑接口实现</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 20:26
 */
@Service
public class BlogServiceImpl implements BlogService {

  @Autowired
  private BlogDao blogDao;

  @Override
  public PageData<BlogDO> queryBlogs(BlogDO query, Integer pageSize, Integer pageNum) {
    if (pageSize == null){
      pageSize = 10;
    }
    if (pageNum == null){
      pageNum = 1;
    }
    PageListResponse<BlogDO> pageListResponse = blogDao.queryBlogs(query, pageNum, pageSize);
    return PageData.<BlogDO>buildPage(pageListResponse.getDataList(), pageListResponse.getCount(), pageNum, pageSize);

  }

  @Override
  public BlogDO queryBlogById(Integer id) {
    return blogDao.queryBlogById(id);
  }
}
