package com.jhon.rain.comment.utils;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * <p>功能描述</br>Bean初始化的工具类</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/13 9:38
 */
public class BeanHelper {

  private static final String UPDATE_TIME_KEY = "updateTime";

  private static final String CREATE_TIME_KEY = "createTime";

  /**
   * <pre>设置默认的属性</pre>
   *
   * @param target 目标
   * @param clazz  转换之后的目标类
   * @param <T>    类型的定义
   */
  public static <T> void setDefaultProp(T target, Class<T> clazz) {
    /** 获取所有的属性 **/
    PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(clazz);
    for (PropertyDescriptor propertyDescriptor : descriptors) {
      String fieldName = propertyDescriptor.getName();
      Object value;
      try {
        value = PropertyUtils.getProperty(target, fieldName);
      } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        throw new RuntimeException("can not set property  when get for " + target + " and clazz " + clazz + " field " + fieldName);
      }
      /** 字符串默认值初始化  **/
      if (String.class.isAssignableFrom(propertyDescriptor.getPropertyType()) && value == null) {
        try {
          PropertyUtils.setProperty(target, fieldName, "");
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
          throw new RuntimeException("can not set property when set for " + target + " and clazz " + clazz + " field " + fieldName);
        }
      }
      /** 数字的默认值初始化 **/
      else if (Number.class.isAssignableFrom(propertyDescriptor.getPropertyType()) && value == null) {
        try {
          BeanUtils.setProperty(target, fieldName, "0");
        } catch (Exception e) {
          throw new RuntimeException("can not set property when set for " + target + " and clazz " + clazz + " field " + fieldName);
        }
      }
    }
  }

  /**
   * <pre>更新更新时间</pre>
   *
   * @param target 目标类
   * @param <T>
   */
  public static <T> void onUpdate(T target) {
    try {
      PropertyUtils.setProperty(target, UPDATE_TIME_KEY, System.currentTimeMillis());
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      e.printStackTrace();
    }
  }

  /**
   * <pre>默认的初始化操作--私有方法</pre>
   *
   * @param target
   * @param clazz
   * @param descriptors
   * @param <T>
   */
  private static <T> void innerDefault(T target, Class<?> clazz, PropertyDescriptor[] descriptors) {
    for (PropertyDescriptor propertyDescriptor : descriptors) {
      String fieldName = propertyDescriptor.getName();
      Object value;
      try {
        value = PropertyUtils.getProperty(target, fieldName);
      } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        throw new RuntimeException("can not set property  when get for " + target + " and clazz " + clazz + " field " + fieldName);
      }

      if (String.class.isAssignableFrom(propertyDescriptor.getPropertyType()) && value == null) {
        try {
          PropertyUtils.setProperty(target, fieldName, "");
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
          throw new RuntimeException("can not set property when set for " + target + " and clazz " + clazz + " field " + fieldName);
        }
      } else if (Number.class.isAssignableFrom(propertyDescriptor.getPropertyType()) && value == null) {
        try {
          BeanUtils.setProperty(target, fieldName, "0");
        } catch (Exception e) {
          throw new RuntimeException("can not set property when set for " + target + " and clazz " + clazz + " field " + fieldName);
        }
      } else if (Date.class.isAssignableFrom(propertyDescriptor.getPropertyType()) && value == null) {
        try {
          BeanUtils.setProperty(target, fieldName, new Date(0));
        } catch (Exception e) {
          throw new RuntimeException("can not set property when set for " + target + " and clazz " + clazz + " field " + fieldName);
        }
      }
    }
  }

  /**
   * <pre>插入的时候初始化</pre>
   *
   * @param target 要初始化的对象
   * @param <T>
   */
  public static <T> void onInsert(T target) {
    Class<?> clazz = target.getClass();
    PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(clazz);
    innerDefault(target, clazz, descriptors);
    long time = System.currentTimeMillis();
    Date date = new Date(time);

    try {
      PropertyUtils.setProperty(target, UPDATE_TIME_KEY, date);
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {

    }

    try {
      PropertyUtils.setProperty(target, CREATE_TIME_KEY, date);
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {

    }
  }
}
