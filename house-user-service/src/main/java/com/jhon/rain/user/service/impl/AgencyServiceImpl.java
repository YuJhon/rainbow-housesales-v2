package com.jhon.rain.user.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jhon.rain.user.mapper.AgencyMapper;
import com.jhon.rain.user.mapper.UserMapper;
import com.jhon.rain.user.model.AgencyDO;
import com.jhon.rain.user.model.UserDO;
import com.jhon.rain.user.model.dto.PageListResponse;
import com.jhon.rain.user.service.AgencyService;
import com.jhon.rain.user.utils.BeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>功能描述</br>经纪人业务逻辑接口实现类</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/22 12:47
 */
@Service
public class AgencyServiceImpl implements AgencyService {

  @Autowired
  private AgencyMapper agencyMapper;

  @Autowired
  private UserMapper userMapper;

  @Value("${file.prefix}")
  private String imgPrefix;


  @Override
  @Transactional(rollbackFor = Exception.class)
  public int addAgency(AgencyDO agency) {
    BeanHelper.onInsert(agency);
    return agencyMapper.insert(agency);
  }

  @Override
  public List<AgencyDO> queryAgencyList() {
    return agencyMapper.queryRecords(new AgencyDO());
  }

  @Override
  public PageListResponse<UserDO> queryAgentList(Integer pageNum, Integer pageSize) {
    Page<UserDO> pageInfo = PageHelper.startPage(pageNum, pageSize);
    List<UserDO> agencies = userMapper.queryAgent(new UserDO());
    setImg(agencies);
    long count = pageInfo.getTotal();
    PageListResponse<UserDO> pageListResp = new PageListResponse<>();
    pageListResp.setCount(count);
    pageListResp.setDataList(agencies);
    return pageListResp;
  }

  @Override
  public AgencyDO queryAgencyDetailById(Integer id) {
    return agencyMapper.queryRecordById(id);
  }

  @Override
  public UserDO queryAgentDetailById(Long id) {
    UserDO user = new UserDO();
    user.setId(id);
    List<UserDO> userList = userMapper.queryAgent(user);
    if (!userList.isEmpty()){
      UserDO agent = userList.get(0);
      agent.setAvatar(imgPrefix+agent.getAvatar());
      AgencyDO agency = new AgencyDO();
      agency.setId(agent.getAgencyId());
      List<AgencyDO> agencies = agencyMapper.queryRecords(agency);
      if (!agencies.isEmpty()){
        agent.setAgencyName(agencies.get(0).getName());
      }
      return agent;
    }
    return null;
  }

  /**
   * <pre>设置头像</pre>
   *
   * @param agencies 需要处理的经纪人列表
   */
  public void setImg(List<UserDO> agencies) {
    agencies.forEach(agent -> {
      agent.setAvatar(imgPrefix + agent.getAvatar());
    });
  }
}
