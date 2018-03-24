package com.jhon.rain.house.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>功能描述</br>节点异常定义</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/21 20:36
 */
@Slf4j
public class NodeItemException extends RuntimeException implements TypeMappingException {

  private CustomType customType;

  public CustomType customType() {
    return customType;
  }

  public NodeItemException() {
  }

  public NodeItemException(CustomType customType, String msg) {
    super(msg);
    this.customType = customType;
  }

  public enum CustomType {
    WRONG_PAGE, WRONG_TYPE, WRONG_PAGE_NUM
  }
}
