package com.jhon.rain.api.dao;

import com.jhon.rain.api.common.RestResponse;
import com.jhon.rain.api.config.GenericRest;
import com.jhon.rain.api.model.CommentDO;
import com.jhon.rain.api.model.vo.CommentReq;
import com.jhon.rain.api.util.RestHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>功能描述</br>评论接口的数据访问层</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 20:27
 */
@Slf4j
@Repository
public class CommentDao {

  @Autowired
  private GenericRest rest;

  @Value("${comment.service.name}")
  private String commentServiceName;

  /**
   * <pre>查询评论列表</pre>
   *
   * @param commentReq
   * @return
   */
  public List<CommentDO> queryCommentList(CommentReq commentReq) {
    return RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(commentServiceName, "/comment/list");
      ResponseEntity<RestResponse<List<CommentDO>>> responseEntity = rest.post(url, commentReq,
              new ParameterizedTypeReference<RestResponse<List<CommentDO>>>() {
              });
      return responseEntity.getBody();
    }).getResult();
  }

  /**
   * <pre>添加评论</pre>
   *
   * @param commentReq
   * @return
   */
  public int addComment(CommentReq commentReq) {
    String result = RestHelper.exec(() -> {
      String url = RestHelper.formatUrl(commentServiceName, "/comment/add");
      ResponseEntity<RestResponse<String>> responseEntity = rest.post(url, commentReq,
              new ParameterizedTypeReference<RestResponse<String>>() {
              });
      return responseEntity.getBody();
    }).getResult();
    log.info("Rest Call Add Comment Result={}", result);
    return 1;
  }
}
