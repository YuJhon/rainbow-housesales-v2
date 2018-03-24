package com.jhon.rain.api.service.impl;

import com.jhon.rain.api.dao.AgencyDao;
import com.jhon.rain.api.model.AgencyDO;
import com.jhon.rain.api.model.HouseDO;
import com.jhon.rain.api.model.UserDO;
import com.jhon.rain.api.model.dto.PageListResponse;
import com.jhon.rain.api.page.PageData;
import com.jhon.rain.api.service.AgencyService;
import com.jhon.rain.api.util.BeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>功能描述</br>经纪人机构业务逻辑接口实现</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/23 10:13
 */
@Service
public class AgencyServiceImpl implements AgencyService {

  @Autowired
  private AgencyDao agencyDao;

  @Override
  public List<AgencyDO> getAllAgency() {
    return agencyDao.getAllAgency();
  }

  @Override
  public PageData<UserDO> getPageAgent(Integer pageNum, Integer pageSize) {
    PageListResponse<UserDO> pageListResponse = agencyDao.queryAgent(pageNum, pageSize);
    return PageData.<UserDO>buildPage(pageListResponse.getDataList(), pageListResponse.getCount(), pageNum, pageSize);
  }

  @Override
  public UserDO getAgentDetail(Long userId) {
    return agencyDao.getAgentDetail(userId);
  }

  @Override
  public int add(AgencyDO agency) {
    BeanHelper.onInsert(agency);
    return agencyDao.add(agency);
  }
}
