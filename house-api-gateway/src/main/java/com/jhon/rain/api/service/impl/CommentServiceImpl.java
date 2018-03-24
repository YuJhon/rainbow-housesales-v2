package com.jhon.rain.api.service.impl;

import com.jhon.rain.api.model.CommentDO;
import com.jhon.rain.api.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>功能描述</br>评论接口业务逻辑实现</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 15:14
 */
@Service
public class CommentServiceImpl implements CommentService {

  @Override
  public List<CommentDO> getHouseComments(Long houseId, int size) {
    return null;
  }
}
