package com.jhon.rain.user.utils;

import java.io.UnsupportedEncodingException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.Map;

/**
 * <p>功能描述</br>Json Web Token认证方式的辅助类</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/22 8:38
 */
public class JWTHelper {

  private static final String SECRET = "session_secret";

  private static final String ISSUER = "house-user";

  /**
   * <pre>获取token</pre>
   *
   * @param claims 参数
   * @return
   */
  public static String generateToken(Map<String, String> claims) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(SECRET);
      JWTCreator.Builder builder = JWT.create().withIssuer(ISSUER).withExpiresAt(DateUtils.addDays(new Date(), 1));
      claims.forEach((k, v) -> {
        builder.withClaim(k, v);
      });
      return builder.sign(algorithm).toString();
    } catch (IllegalArgumentException | UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * <pre>校验token</pre>
   *
   * @param token 需要校验的token
   * @return
   */
  public static Map<String, String> verifyToken(String token) {
    Algorithm algorithm = null;
    try {
      algorithm = Algorithm.HMAC256(SECRET);
    } catch (IllegalArgumentException | UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
    DecodedJWT jwt = verifier.verify(token);
    Map<String, Claim> map = jwt.getClaims();
    Map<String, String> resultMap = Maps.newHashMap();
    map.forEach((k, v) -> {
      resultMap.put(k, v.asString());
    });
    return resultMap;
  }
}
