package com.jhon.rain.api.controller;

import com.jhon.rain.api.common.CommonConstants;
import com.jhon.rain.api.model.HouseDO;
import com.jhon.rain.api.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 17:15
 */
@Controller
public class HomePageController {

  @Autowired
  private HouseService houseService;

  /**
   * <pre>默认进入到首页</pre>
   *
   * @param modelMap
   * @return
   */
  @GetMapping("/")
  public String index(ModelMap modelMap) {
    return "redirect:/index";
  }

  /**
   * <pre>跳转到home页面</pre>
   *
   * @param modelMap
   * @return
   */
  @GetMapping("/index")
  public String homePage(ModelMap modelMap) {
    List<HouseDO> houseList = houseService.getLatestHouse(CommonConstants.RECOM_SIZE);
    modelMap.put("recomHouses", houseList);
    return "homepage/index";
  }
}
