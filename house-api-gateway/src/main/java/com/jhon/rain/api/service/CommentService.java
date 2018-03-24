package com.jhon.rain.api.service;

import com.jhon.rain.api.model.CommentDO;

import java.util.List;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 15:14
 */
public interface CommentService {
  /**
   * <pre>获取房产的评论信息</pre>
   *
   * @param houseId 房产id
   * @param size    查询条数
   * @return
   */
  List<CommentDO> getHouseComments(Long houseId, int size);
}
