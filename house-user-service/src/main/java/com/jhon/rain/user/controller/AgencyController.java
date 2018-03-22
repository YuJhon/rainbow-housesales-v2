package com.jhon.rain.user.controller;

import com.jhon.rain.user.common.RestResponse;
import com.jhon.rain.user.model.AgencyDO;
import com.jhon.rain.user.model.UserDO;
import com.jhon.rain.user.model.dto.PageListResponse;
import com.jhon.rain.user.service.AgencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>功能描述</br>经纪人机构的控制器</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/22 12:45
 */
@Slf4j
@RestController
@RequestMapping("agency")
public class AgencyController {

  @Autowired
  private AgencyService agencyService;

  /**
   * <pre>新增经纪人机构</pre>
   *
   * @param agency
   * @return
   */
  @PostMapping("add")
  public RestResponse<String> addAgency(@RequestBody AgencyDO agency) {
    agencyService.addAgency(agency);
    return RestResponse.success();
  }

  /**
   * <pre>查询经纪机构列表</pre>
   *
   * @return
   */
  @GetMapping("list")
  public RestResponse<List<AgencyDO>> queryAgencyList() {
    List<AgencyDO> agencyList = agencyService.queryAgencyList();
    return RestResponse.success(agencyList);
  }

  /**
   * <pre>查询经纪人</pre>
   *
   * @param pageNum
   * @param pageSize
   * @return
   */
  @GetMapping("agentList")
  public RestResponse<PageListResponse<UserDO>> queryAgentList(@RequestParam(name = "pageNum") Integer pageNum,
                                                               @RequestParam(name = "pageSize") Integer pageSize) {
    PageListResponse<UserDO> pageListResp = agencyService.queryAgentList(pageNum, pageSize);
    return RestResponse.success(pageListResp);
  }

  /**
   * <pre>查询经纪机构详情</pre>
   * @param id
   * @return
   */
  @GetMapping("agencyDetail")
  public RestResponse<AgencyDO> queryAgencyDetail(@RequestParam(name = "id") Integer id) {
    AgencyDO agency = agencyService.queryAgencyDetailById(id);
    return RestResponse.success(agency);
  }


  /**
   * <pre>查询经纪人详情</pre>
   * @param id
   * @return
   */
  @GetMapping("agentDetail")
  public RestResponse<UserDO> queryAgentDetail(@RequestParam(name = "id") Long id) {
    UserDO user = agencyService.queryAgentDetailById(id);
    return RestResponse.success(user);
  }
}
