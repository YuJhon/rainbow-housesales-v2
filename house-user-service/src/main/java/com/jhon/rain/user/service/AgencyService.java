package com.jhon.rain.user.service;

import com.jhon.rain.user.model.AgencyDO;
import com.jhon.rain.user.model.UserDO;
import com.jhon.rain.user.model.dto.PageListResponse;

import java.util.List;

/**
 * <p>功能描述</br>经纪人业务逻辑接口定义</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/22 12:46
 */
public interface AgencyService {
  /**
   * <pre>添加经纪人机构</pre>
   *
   * @param agency
   * @return
   */
  int addAgency(AgencyDO agency);

  /**
   * <pre>查询经纪机构</pre>
   *
   * @return
   */
  List<AgencyDO> queryAgencyList();

  /**
   * <pre>查询所有的经纪机构</pre>
   *
   * @param pageNum  当前页
   * @param pageSize 每页
   * @return
   */
  PageListResponse<UserDO> queryAgentList(Integer pageNum, Integer pageSize);

  /**
   * <pre>查询经纪机构</pre>
   *
   * @param id 主键ID
   * @return
   */
  AgencyDO queryAgencyDetailById(Integer id);

  /**
   * <pre>查询经纪人</pre>
   *
   * @param id
   * @return
   */
  UserDO queryAgentDetailById(Long id);
}
