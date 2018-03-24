package com.jhon.rain.api.controller;

import com.jhon.rain.api.common.CommonConstants;
import com.jhon.rain.api.common.ResultMsg;
import com.jhon.rain.api.interceptor.UserContext;
import com.jhon.rain.api.model.AgencyDO;
import com.jhon.rain.api.model.HouseDO;
import com.jhon.rain.api.model.UserDO;
import com.jhon.rain.api.page.PageData;
import com.jhon.rain.api.service.AgencyService;
import com.jhon.rain.api.service.HouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * <p>功能描述</br>经纪人机构的控制器</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/23 8:42
 */
@Slf4j
@Controller
@RequestMapping("agency")
public class AgencyAPIController {

  @Autowired
  private AgencyService agencyService;

  @Autowired
  private HouseService houseService;


  /**
   * <pre>添加经纪人</pre>
   *
   * @return
   */
  @GetMapping("/create")
  public String createAgency() {
    UserDO user = UserContext.getUser();
    if (user == null || !Objects.equals("jiangy19@126.com", user.getEmail())) {
      return "redirect:/accounts/signin?" + ResultMsg.successMsg("请先登录").asUrlParams();
    }
    return "user/agency/create";
  }

  /**
   * <pre>添加经纪人机构</pre>
   *
   * @param agency
   * @return
   */
  @RequestMapping("/submit")
  public String agencySubmit(AgencyDO agency) {
    UserDO user = UserContext.getUser();
    if (user == null || !Objects.equals("jiangy19@126.com", user.getEmail())) {
      return "redirect:/accounts/signin?" + ResultMsg.successMsg("请先登录").asUrlParams();
    }
    agencyService.add(agency);
    return "redirect:/index?" + ResultMsg.errorMsg("创建成功").asUrlParams();
  }

  /**
   * <pre>查询经纪人列表</pre>
   *
   * @param pageNum  分页的当前页
   * @param pageSize 每页的大小
   * @param modelMap 参数信息载体
   * @return 经纪人列表
   */
  @GetMapping("/agentList")
  public String agentList(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(name = "pageSize", defaultValue = "6") Integer pageSize,
                          ModelMap modelMap) {
    PageData<UserDO> ps = agencyService.getPageAgent(pageNum, pageSize);
    List<HouseDO> houses = houseService.getHotHouses(CommonConstants.RECOM_SIZE);
    modelMap.put("recomHouses", houses);
    modelMap.put("ps", ps);
    return "user/agent/agentList";
  }

  /**
   * <pre>获取经纪人详情</pre>
   *
   * @param id       经纪人Id
   * @param modelMap
   * @return
   */
  @RequestMapping("/agentDetail")
  public String agentDetail(Long id, ModelMap modelMap) {
    UserDO user = agencyService.getAgentDetail(id);
    List<HouseDO> houses = houseService.getHotHouses(CommonConstants.RECOM_SIZE);
    HouseDO query = new HouseDO();
    query.setUserId(id);
    query.setBookmarked(false);
    PageData<HouseDO> bindHouse = houseService.queryHouse(query, 1, 3);
    if (bindHouse != null) {
      modelMap.put("bindHouses", bindHouse.getList());
    }
    modelMap.put("recomHouses", houses);
    modelMap.put("agent", user);
    modelMap.put("agencyName", user.getAgencyName());
    return "/user/agent/agentDetail";
  }

  /**
   * <pre>获取经纪机构列表</pre>
   *
   * @param modelMap 携带信息到页面
   * @return
   */
  @GetMapping("/list")
  public String agencyList(ModelMap modelMap) {
    List<AgencyDO> agencies = agencyService.getAllAgency();
    List<HouseDO> houses = houseService.getHotHouses(CommonConstants.RECOM_SIZE);
    modelMap.put("recomHouses", houses);
    modelMap.put("agencyList", agencies);
    return "/user/agency/agencyList";
  }

  /**
   * <pre>留言</pre>
   *
   * @param id
   * @param msg
   * @param email
   * @param modelMap
   * @return
   */
  @RequestMapping("/agentMsg")
  public String agentMsg(Long id, String msg, String email, ModelMap modelMap) {
    UserDO user = agencyService.getAgentDetail(id);
    //mailService.sendSimpleMail("来自"+ email +"的咨询", msg, user.getEmail());
    return "redirect:/agency/agentDetail?id=" + id + "&" + ResultMsg.successMsg("留言成功").asUrlParams();
  }
}
