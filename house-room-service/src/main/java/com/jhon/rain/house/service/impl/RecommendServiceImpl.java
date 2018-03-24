package com.jhon.rain.house.service.impl;

import com.jhon.rain.house.model.HouseDO;
import com.jhon.rain.house.service.HouseService;
import com.jhon.rain.house.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 12:01
 */
@Service
public class RecommendServiceImpl implements RecommendService {

  @Autowired
  private StringRedisTemplate redisTemplate;

  @Autowired
  private HouseService houseService;

  private static final String HOT_HOUSE_KEY = "_hot_house";

  @Override
  public List<HouseDO> getHotHouse(Integer size) {
    Set<String> idSet = redisTemplate.opsForZSet().reverseRange(HOT_HOUSE_KEY, 0, -1);
    List<Long> ids = idSet.stream().map(id -> Long.parseLong(id)).collect(Collectors.toList());
    HouseDO query = new HouseDO();
    query.setIds(ids);
    return houseService.queryHouseAndSetImg(query, size);
  }

  @Override
  public List<HouseDO> getLatestHouse() {
    HouseDO query = new HouseDO();
    query.setSort("create_time");
    return houseService.queryHouseAndSetImg(query, 8);
  }

  @Override
  public void increaseHot(long houseId) {
    /** 存在就加1，不存在就设置为1 **/
    redisTemplate.opsForZSet().incrementScore(HOT_HOUSE_KEY, "" + houseId, 1.00D);
    /** 保留10个 **/
    redisTemplate.opsForZSet().removeRange(HOT_HOUSE_KEY, 0, -11);
  }
}
