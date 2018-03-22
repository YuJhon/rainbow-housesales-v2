package com.jhon.rain.user.mapper;

import com.jhon.rain.user.model.AgencyDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>功能描述</br>经纪人数据访问层</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/22 12:48
 */
@Mapper
public interface AgencyMapper {

  /**
   * <pre>插入经纪机构</pre>
   *
   * @param agency
   * @return
   */
  int insert(AgencyDO agency);

  /**
   * <pre>查询经纪机构</pre>
   *
   * @param agencyDO 经纪机构
   * @return
   */
  List<AgencyDO> queryRecords(AgencyDO agencyDO);

  /**
   * <pre>根据id查询经纪机构</pre>
   *
   * @param id 主键ID
   * @return
   */
  AgencyDO queryRecordById(@Param("id") Integer id);
}
