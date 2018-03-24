package com.jhon.rain.house.mapper;

import com.jhon.rain.house.model.CityDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 12:34
 */
@Mapper
public interface CityMapper {

  /**
   * <pre>查询所有的城市</pre>
   *
   * @return
   */
  List<CityDO> queryAll();
}
