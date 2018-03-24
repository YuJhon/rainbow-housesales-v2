package com.jhon.rain.house.service.impl;

import com.jhon.rain.house.dao.UserDao;
import com.jhon.rain.house.helper.MailSenderHelper;
import com.jhon.rain.house.mapper.HouseMsgMapper;
import com.jhon.rain.house.model.HouseMsgDO;
import com.jhon.rain.house.model.UserDO;
import com.jhon.rain.house.service.HouseMsgService;
import com.jhon.rain.house.utils.BeanHelper;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 11:56
 */
@Service
public class HouseMsgServiceImpl implements HouseMsgService {

  @Autowired
  private HouseMsgMapper houseMsgMapper;

  @Autowired
  private UserDao userDao;

  @Autowired
  private MailSenderHelper mailSenderHelper;

  @Override
  public int addHouseMsg(HouseMsgDO houseMsg) {
    int record = 0;
    BeanHelper.setDefaultProp(houseMsg,HouseMsgDO.class);
    BeanHelper.onInsert(houseMsg);
    record = houseMsgMapper.addHouseMsg(houseMsg);
    /** 发送邮件 **/
    UserDO user = userDao.getAgentDetail(houseMsg.getAgentId());
    mailSenderHelper.sendSimpleCustomEmail("来自用户" + houseMsg.getEmail(), houseMsg.getMsg(), user.getEmail());
    return record;
  }
}
