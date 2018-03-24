package com.jhon.rain.comment.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Strings;
import com.jhon.rain.comment.dao.UserDao;
import com.jhon.rain.comment.mapper.CommentMapper;
import com.jhon.rain.comment.model.CommentDO;
import com.jhon.rain.comment.model.UserDO;
import com.jhon.rain.comment.service.CommentService;
import com.jhon.rain.comment.utils.BeanHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/24 19:17
 */
@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

  @Autowired
  private CommentMapper commentMapper;

  @Autowired
  private StringRedisTemplate redisTemplate;

  @Autowired
  private UserDao userDao;

  @Override
  public int addHouseComment(Long houseId, Long userId, String content) {
    return addComment(1, houseId, null, userId, content);
  }

  @Override
  public int addBlogComment(Integer blogId, Long userId, String content) {
    return addComment(1, null, blogId, userId, content);
  }

  /**
   * <pre>添加评论</pre>
   *
   * @param type    类型
   * @param houseId 房产Id
   * @param blogId  博客Id
   * @param userId  用户Id
   * @param content 内容
   * @return
   */
  @Transactional(rollbackFor = Exception.class)
  private Integer addComment(Integer type, Long houseId, Integer blogId, Long userId, String content) {
    Integer record = 0;
    String key = null;
    CommentDO comment = new CommentDO();
    if (type == 1) {
      comment.setHouseId(houseId);
      key = "house_comments_" + houseId;
    } else {
      comment.setBlogId(blogId);
      key = "blog_comments_" + blogId;
    }
    comment.setContent(content);
    comment.setUserId(userId);
    comment.setType(type);
    BeanHelper.onInsert(comment);
    BeanHelper.setDefaultProp(comment, CommentDO.class);
    record = commentMapper.insert(comment);
    redisTemplate.delete(redisTemplate.keys(key + ""));
    return record;
  }

  @Override
  public List<CommentDO> getHouseComments(Long houseId, Integer size) {
    String key = "house_comments" + "_" + houseId + "_" + size;
    String json = redisTemplate.opsForValue().get(key);
    List<CommentDO> lists = null;
    if (Strings.isNullOrEmpty(json)) {
      lists = doGetHouseComments(houseId, size);
      redisTemplate.opsForValue().set(key, JSON.toJSONString(lists));
      redisTemplate.expire(key, 5, TimeUnit.MINUTES);
    } else {
      lists = JSON.parseObject(json, new TypeReference<List<CommentDO>>() {
      });
    }
    return lists;
  }

  /**
   * <pre>从数据库中查询</pre>
   *
   * @param houseId
   * @param size
   * @return
   */
  private List<CommentDO> doGetHouseComments(Long houseId, Integer size) {
    List<CommentDO> comments = commentMapper.queryHouseComments(houseId, size);
    return constructUserInfo(comments);
  }

  @Override
  public List<CommentDO> getBlogComments(Integer blogId, Integer size) {
    String key = "blog_comments" + "_" + blogId + "_" + size;
    String json = redisTemplate.opsForValue().get(key);
    List<CommentDO> lists = null;
    if (Strings.isNullOrEmpty(json)) {
      lists = doGetBlogComments(blogId, size);
      redisTemplate.opsForValue().set(key, JSON.toJSONString(lists));
      redisTemplate.expire(key, 5, TimeUnit.MINUTES);
    } else {
      lists = JSON.parseObject(json, new TypeReference<List<CommentDO>>() {
      });
    }
    return lists;
  }

  /**
   * <pre>从数据库中查询</pre>
   *
   * @param blogId
   * @param size
   * @return
   */
  private List<CommentDO> doGetBlogComments(Integer blogId, Integer size) {
    List<CommentDO> comments = commentMapper.queryBlogComments(blogId, size);
    return constructUserInfo(comments);
  }

  /**
   * <pre>组织用户信息</pre>
   *
   * @param targets
   * @return
   */
  private List<CommentDO> constructUserInfo(List<CommentDO> targets) {
    targets.forEach(comment -> {
      Long userId = comment.getUserId();
      UserDO user = userDao.getUserDetailById(userId);
      comment.setAvatar(user.getAvatar());
      comment.setUsername(user.getName());
    });
    return targets;
  }
}
