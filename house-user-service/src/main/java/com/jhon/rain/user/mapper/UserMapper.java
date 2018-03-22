package com.jhon.rain.user.mapper;

import com.jhon.rain.user.model.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>功能描述</br>用于数据访问层</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/21 21:51
 */
@Mapper
public interface UserMapper {

  /**
   * <pre>通过id查询用户</pre>
   *
   * @param id
   * @return
   */
  UserDO queryUserById(Long id);

  /**
   * <pre>查询所有的用户</pre>
   *
   * @param user
   * @return
   */
  List<UserDO> query(UserDO user);

  /**
   * <pre>更新用户信息</pre>
   *
   * @param user
   * @return
   */
  int update(UserDO user);

  /**
   * <pre>插入用户</pre>
   *
   * @param user
   * @return
   */
  int insert(UserDO user);

  /**
   * <pre>通过邮件查询用户</pre>
   *
   * @param email 用户邮箱
   * @return
   */
  UserDO queryUserByEmail(String email);

  /**
   * <pre>查询经纪人</pre>
   *
   * @param userDO
   * @return
   */
  List<UserDO> queryAgent(@Param("user") UserDO userDO);
}
