package com.jhon.rain.house.model.dto;

import lombok.Data;

import java.util.List;

/**
 * <p>功能描述</br>分页查询列表数据载体</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/22 13:43
 */
@Data
public class PageListResponse<T> {

  /**
   * 数据列表
   */
  private List<T> dataList;
  /**
   * 总共条数
   */
  private Long count;

  public static <T> PageListResponse<T> build(List<T> dataList, Long count) {
    PageListResponse<T> pageListResp = new PageListResponse<>();
    pageListResp.setDataList(dataList);
    pageListResp.setCount(count);
    return pageListResp;
  }
}
