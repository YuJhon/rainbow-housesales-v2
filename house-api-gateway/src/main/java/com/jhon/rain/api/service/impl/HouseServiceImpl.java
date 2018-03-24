package com.jhon.rain.api.service.impl;

import com.google.common.base.Joiner;
import com.jhon.rain.api.HouseUserTypeEnum;
import com.jhon.rain.api.dao.HouseDao;
import com.jhon.rain.api.model.*;
import com.jhon.rain.api.model.dto.PageListResponse;
import com.jhon.rain.api.page.PageData;
import com.jhon.rain.api.service.FileService;
import com.jhon.rain.api.service.HouseService;
import com.jhon.rain.api.util.BeanHelper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>功能描述</br>房产的业务逻辑实现接口</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 14:53
 */
@Service
public class HouseServiceImpl implements HouseService {

  @Autowired
  private HouseDao houseDao;

  @Autowired
  private FileService fileService;

  @Override
  public List<HouseDO> getHotHouses(int size) {
    return houseDao.queryHotHouses(size);
  }

  @Override
  public List<HouseDO> getLatestHouse(int size) {
    return houseDao.queryLatestHouses(size);
  }

  @Override
  public PageData<HouseDO> queryHouse(HouseDO query, Integer pageNum, Integer pageSize) {
    PageListResponse<HouseDO> pageListResponse = houseDao.queryHouse(query, pageNum, pageSize);
    return PageData.<HouseDO>buildPage(pageListResponse.getDataList(), pageListResponse.getCount(), pageNum, pageSize);
  }

  @Override
  public HouseDO queryHouseById(Long houseId) {
    return houseDao.queryHouseById(houseId);
  }

  @Override
  public int addHouseMsg(HouseMsgDO houseMsg) {
    return houseDao.addHouseMsg(houseMsg);
  }

  @Override
  public int updateRating(Long id, Double rating) {
    return houseDao.updateRating(id, rating);
  }

  @Override
  public int bindUser2House(Long houseId, Long userId, HouseUserTypeEnum bookmark) {
    return houseDao.bindUser2House(houseId, userId, bookmark);
  }

  @Override
  public int unbindUser2House(Long houseId, Long userId, HouseUserTypeEnum bookmark) {
    return houseDao.unbindUser2House(houseId, userId, bookmark);
  }

  @Override
  public List<CityDO> getAllCities() {
    return houseDao.getAllCities();
  }

  @Override
  public List<CommunityDO> getAllCommunities() {
    return houseDao.getAllCommunities();
  }

  @Override
  public int addHouse(HouseDO house, UserDO user) {
    /** 1.添加房屋图片 **/
    if (CollectionUtils.isNotEmpty(house.getHouseFiles())) {
      String images = Joiner.on(",").join(fileService.getImgPaths(house.getHouseFiles()));
      house.setImages(images);
    }
    /** 2.添加户型图片 **/
    if (CollectionUtils.isNotEmpty(house.getFloorPlanFiles())) {
      String imgs = Joiner.on(",").join(fileService.getImgPaths(house.getFloorPlanFiles()));
      house.setFloorPlan(imgs);
    }
    /** 3.插入房产信息 **/
    BeanHelper.onInsert(house);
    return houseDao.addHouse(house);
  }
}
