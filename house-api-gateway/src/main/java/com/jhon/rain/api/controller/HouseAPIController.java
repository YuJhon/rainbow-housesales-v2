package com.jhon.rain.api.controller;

import com.google.common.collect.Lists;
import com.jhon.rain.api.HouseUserTypeEnum;
import com.jhon.rain.api.common.CommonConstants;
import com.jhon.rain.api.common.ResultMsg;
import com.jhon.rain.api.interceptor.UserContext;
import com.jhon.rain.api.model.*;
import com.jhon.rain.api.page.PageData;
import com.jhon.rain.api.service.AgencyService;
import com.jhon.rain.api.service.CommentService;
import com.jhon.rain.api.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>功能描述</br>房产API控制器</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 14:51
 */
@Controller
@RequestMapping("house")
public class HouseAPIController {

  @Autowired
  private HouseService houseService;

  @Autowired
  private AgencyService agencyService;

  @Autowired
  private CommentService commentService;

  /**
   * <pre>房产列表</pre>
   *
   * @param pageNum  当前页
   * @param pageSize 每页大小
   * @param name     小区名称
   * @param type     房屋类型
   * @return 房产列表
   */
  @RequestMapping("/list")
  public String queryByPage(@RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                            @RequestParam(name = "name", required = false) String name,
                            @RequestParam(name = "type", required = false) Integer type,
                            ModelMap modelMap
  ) {
    /** 查询房产列表 **/
    HouseDO query = new HouseDO();
    query.setName(name);
    query.setType(type);
    PageData<HouseDO> ps = houseService.queryHouse(query, pageNum, pageSize);
    List<HouseDO> hotHouses = houseService.getLatestHouse(CommonConstants.RECOM_SIZE);
    modelMap.put("ps", ps);
    modelMap.put("recomHouses", hotHouses);
    modelMap.put("vo", query);
    return "/house/listing";
  }

  /**
   * <pre>查询房产的详细信息</pre>
   *
   * @param houseId  房产Id
   * @param modelMap 返回页面携带参数的实体
   * @return
   */
  @RequestMapping("/detail")
  public String houseDetail(@RequestParam(name = "id") Long houseId, ModelMap modelMap) {
    /** 1.查询房产信息 **/
    HouseDO house = houseService.queryHouseById(houseId);
    /** 2.获取评论 **/
    int commentSize = 8;
    List<CommentDO> comments = commentService.getHouseComments(houseId, commentSize);
    if (house.getUserId() != null && !Integer.valueOf(0).equals(house.getUserId())) {
      modelMap.put("agent", agencyService.getAgentDetail(house.getUserId()));
    }
    if (comments == null) {
      comments = Lists.newArrayList();
    }
    /** 3.获取推荐的房产 **/
    List<HouseDO> rcHouses = houseService.getLatestHouse(CommonConstants.RECOM_SIZE);
    modelMap.put("recomHouses", rcHouses);
    modelMap.put("house", house);
    modelMap.put("commentList", comments);
    return "/house/detail";
  }

  /**
   * <pre>留言</pre>
   *
   * @param houseMsg 房产留言信息载体
   * @return 房产详情页
   */
  @RequestMapping("/leaveMsg")
  public String houseMsg(HouseMsgDO houseMsg) {
    houseService.addHouseMsg(houseMsg);
    return "redirect:/house/detail?id=" + houseMsg.getHouseId() + ResultMsg.successMsg("留言成功！").asUrlParams();
  }

  /**
   * <pre>1.评分</pre>
   *
   * @param rating 评分
   * @param id     主键ID
   * @return
   */
  @ResponseBody
  @RequestMapping("/rating")
  public ResultMsg houseRate(Double rating, Long id) {
    houseService.updateRating(id, rating);
    return ResultMsg.successMsg("ok");
  }

  /**
   * <pre>2.收藏</pre>
   *
   * @param id 房产Id
   * @return
   */
  @ResponseBody
  @RequestMapping("/bookmark")
  public ResultMsg bookmark(Long id) {
    UserDO user = UserContext.getUser();
    houseService.bindUser2House(id, user.getId(), HouseUserTypeEnum.BOOKMARK);
    return ResultMsg.successMsg("ok");
  }

  /**
   * <pre>3.取消收藏</pre>
   *
   * @param id 房产Id
   * @return
   */
  @ResponseBody
  @RequestMapping("/unbookmark")
  public ResultMsg unBookMark(Long id) {
    UserDO user = UserContext.getUser();
    houseService.unbindUser2House(id, user.getId(), HouseUserTypeEnum.BOOKMARK);
    return ResultMsg.successMsg("ok");
  }

  /**
   * <pre>删除房产</pre>
   *
   * @param id       房产Id
   * @param pageType 类型
   * @return
   */
  @RequestMapping(value = "/del")
  public String deleteSaleHouse(Long id, String pageType) {
    UserDO user = UserContext.getUser();
    houseService.unbindUser2House(id, user.getId(), pageType.equals("own") ? HouseUserTypeEnum.SALE : HouseUserTypeEnum.BOOKMARK);
    return "redirect:/house/ownlist";
  }

  /**
   * <pre>收藏列表</pre>
   *
   * @param house    房产
   * @param pageNum  当前页
   * @param pageSize 每页大小
   * @param modelMap
   * @return
   */
  @RequestMapping("/bookmarked")
  public String bookmarkedList(HouseDO house, Integer pageNum, Integer pageSize, ModelMap modelMap) {
    UserDO user = UserContext.getUser();
    house.setBookmarked(true);
    house.setUserId(user.getId());
    PageData<HouseDO> ps = houseService.queryHouse(house, pageNum, pageSize);
    modelMap.put("ps", ps);
    modelMap.put("pageType", "book");
    return "/house/ownlist";
  }

  /**
   * <pre>跳转到添加房产的页面</pre>
   *
   * @param modelMap
   * @return 添加房产的页面
   */
  @GetMapping("/toAdd")
  public String toAdd(ModelMap modelMap) {
    modelMap.put("cities", houseService.getAllCities());
    modelMap.put("communitys", houseService.getAllCommunities());
    return "/house/add";
  }

  /**
   * <pre>添加房产的操作</pre>
   *
   * @param house 房产信息
   * @return 当前登录用户的房产信息查询接口
   */
  @RequestMapping("/add")
  public String doAdd(HouseDO house) {
    /** 添加房产的业务 **/
    UserDO user = UserContext.getUser();
    houseService.addHouse(house, user);
    return "redirect:/house/ownlist" + ResultMsg.successMsg("添加成功").asUrlParams();
  }
}
