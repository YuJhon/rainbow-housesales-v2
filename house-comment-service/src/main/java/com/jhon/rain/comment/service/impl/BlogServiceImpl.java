package com.jhon.rain.comment.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.jhon.rain.comment.mapper.BlogMapper;
import com.jhon.rain.comment.model.BlogDO;
import com.jhon.rain.comment.model.dto.PageListResponse;
import com.jhon.rain.comment.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 19:17
 */
@Slf4j
@Service
public class BlogServiceImpl implements BlogService {

  @Autowired
  private BlogMapper blogMapper;

  @Override
  public PageListResponse<BlogDO> queryBlogList(BlogDO query, Integer pageNum, Integer pageSize) {
    Page<BlogDO> pageInfo = PageHelper.startPage(pageNum, pageSize);
    List<BlogDO> agencies = blogMapper.queryBlog(query);
    long count = pageInfo.getTotal();
    PageListResponse<BlogDO> pageListResp = new PageListResponse<>();
    pageListResp.setCount(count);
    pageListResp.setDataList(agencies);
    return pageListResp;
  }

  @Override
  public BlogDO queryOneBlogById(Integer id) {
    return blogMapper.queryRecordById(id);
  }

  /**
   * <pre>数据加工</pre>
   *
   * @param blogs
   */
  private void populate(List<BlogDO> blogs) {
    if (!blogs.isEmpty()) {
      blogs.stream().forEach(item -> {
        String stripped = Jsoup.parse(item.getContent()).text();
        item.setDigest(stripped.substring(0, Math.min(stripped.length(), 40)));
        String tags = item.getTags();
        item.getTagList().addAll(Lists.newArrayList(Splitter.on(",").split(tags)));
      });
    }
  }
}
