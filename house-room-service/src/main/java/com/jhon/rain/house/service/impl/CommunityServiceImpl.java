package com.jhon.rain.house.service.impl;

import com.jhon.rain.house.mapper.CommunityMapper;
import com.jhon.rain.house.model.CommunityDO;
import com.jhon.rain.house.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 11:21
 */
@Service
public class CommunityServiceImpl implements CommunityService {

  @Autowired
  private CommunityMapper communityMapper;

  @Override
  public List<CommunityDO> queryAll() {
    return communityMapper.queryAll();
  }
}
