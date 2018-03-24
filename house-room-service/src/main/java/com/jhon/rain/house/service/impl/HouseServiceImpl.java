package com.jhon.rain.house.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jhon.rain.house.enmus.HouseUserTypeEnum;
import com.jhon.rain.house.mapper.*;
import com.jhon.rain.house.model.CommunityDO;
import com.jhon.rain.house.model.HouseDO;
import com.jhon.rain.house.model.HouseUserDO;
import com.jhon.rain.house.model.dto.PageListResponse;
import com.jhon.rain.house.service.HouseService;
import com.jhon.rain.house.utils.BeanHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 10:53
 */
@Service
public class HouseServiceImpl implements HouseService {

  @Autowired
  private HouseMapper houseMapper;

  @Autowired
  private HouseUserMapper houseUserMapper;

  @Autowired
  private CommunityMapper communityMapper;


  @Value("${file.prefix}")
  private String imgPrefix;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int unBindUser2House(Long houseId, Long userId, HouseUserTypeEnum houseUserType) {
    int result = 0;
    if (HouseUserTypeEnum.SALE.equals(houseUserType)) {
      result = houseMapper.downHouse(houseId);
    } else {
      result = houseUserMapper.deleteHouseUser(houseId, userId, houseUserType.value);
    }
    return result;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int bindUser2House(Long houseId, Long userId, HouseUserTypeEnum houseUserType) {
    HouseUserDO queryHouseUser = houseUserMapper.queryHouseUser(houseId, userId, houseUserType.value);
    if (queryHouseUser != null) {
      return 1;
    }
    HouseUserDO houseUser = new HouseUserDO();
    houseUser.setHouseId(houseId);
    houseUser.setUserId(userId);
    houseUser.setType(houseUserType.value);
    BeanHelper.setDefaultProp(houseUser, HouseUserDO.class);
    BeanHelper.onInsert(houseUser);
    return houseUserMapper.insertHouseUser(houseUser);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int addHouse(HouseDO house) {
    /** 1.添加房产 **/
    BeanHelper.setDefaultProp(house, HouseDO.class);
    BeanHelper.onInsert(house);
    houseMapper.insert(house);
    /** 2.绑定用户到房产  **/
    Long userId = house.getUserId();
    return bindUser2House(house.getId(), userId, HouseUserTypeEnum.SALE);
  }

  @Override
  public PageListResponse<HouseDO> queryHouseList(HouseDO query, Integer pageNum, Integer pageSize) {
    /** 先根据名称查询对应的社区 **/
    if (StringUtils.isNoneBlank(query.getName())) {
      CommunityDO community = new CommunityDO();
      community.setName(query.getName());
      List<CommunityDO> communities = communityMapper.queryCommunities(community);
      if (!communities.isEmpty()) {
        query.setCommunityId(communities.get(0).getId());
      }
    }
    /** 分页查询房产 **/
    Page<HouseDO> pageInfo = PageHelper.startPage(pageNum, pageSize);
    List<HouseDO> houseList = houseMapper.queryHouseList(query);
    /** 设置图片和户型图 **/
    houseList.forEach(house -> {
      house.setFirstImg(imgPrefix + house.getFirstImg());
      house.setImageList(house.getImageList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
      house.setFloorPlanList(house.getFloorPlanList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
    });
    long count = pageInfo.getTotal();
    PageListResponse<HouseDO> pageListResp = new PageListResponse<>();
    pageListResp.setCount(count);
    pageListResp.setDataList(houseList);
    return pageListResp;
  }

  @Override
  public HouseDO queryDetailById(Long id) {
    HouseDO house = houseMapper.queryDetailById(id);
    house.setFirstImg(imgPrefix + house.getFirstImg());
    house.setImageList(house.getImageList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
    house.setFloorPlanList(house.getFloorPlanList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
    return house;
  }

  @Override
  public int updateRating(Long houseId, Double rating) {
    HouseDO house = queryDetailById(houseId);
    Double oldRating = house.getRating();
    Double newRating = oldRating.equals(0D) ? rating : Math.min(Math.round(oldRating + rating) / 2, 5);
    HouseDO updateHouse = new HouseDO();
    updateHouse.setId(houseId);
    updateHouse.setRating(newRating);
    return houseMapper.updateHouseRating(updateHouse);
  }

  @Override
  public List<HouseDO> queryHouseAndSetImg(HouseDO query,Integer size) {
    /** 分页查询房产 **/
    PageHelper.startPage(1, size);
    List<HouseDO> houseList = houseMapper.queryHouseList(query);
    /** 设置图片和户型图 **/
    houseList.forEach(house -> {
      house.setFirstImg(imgPrefix + house.getFirstImg());
      house.setImageList(house.getImageList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
      house.setFloorPlanList(house.getFloorPlanList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
    });
    return houseList;
  }
}
