package com.jhon.rain.comment.utils;

import com.google.common.base.Throwables;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * <p>功能描述</br> 密码加密工具 </p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v1
 * @date 2018/3/21 22:42
 */
public class HashUtils {

  private static final HashFunction FUNCTION = Hashing.md5();

  private static final HashFunction MURMUR_FUNC = Hashing.murmur3_128();

  private static final String SALT = "jhon.rain";

  /**
   * <pre>MD5加密</pre>
   *
   * @param password 密码
   * @return 加密后的字符串
   */
  public static String encryPassword(String password) {
    HashCode hashCode = FUNCTION.hashString(password + SALT, Charset.forName("UTF-8"));
    return hashCode.toString();
  }

  /**
   * <pre>hash算法</pre>
   *
   * @param input
   * @return
   */
  public static String hashString(String input) {
    HashCode code = null;
    try {
      code = MURMUR_FUNC.hashBytes(input.getBytes("utf-8"));
    } catch (UnsupportedEncodingException e) {
      Throwables.propagate(e);
    }
    return code.toString();
  }
}
