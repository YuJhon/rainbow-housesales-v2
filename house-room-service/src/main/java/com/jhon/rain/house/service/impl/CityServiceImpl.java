package com.jhon.rain.house.service.impl;

import com.jhon.rain.house.mapper.CityMapper;
import com.jhon.rain.house.model.CityDO;
import com.jhon.rain.house.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 11:15
 */
@Service
public class CityServiceImpl implements CityService {

  @Autowired
  private CityMapper cityMapper;

  @Override
  public List<CityDO> queryAll() {
    return cityMapper.queryAll();
  }
}
