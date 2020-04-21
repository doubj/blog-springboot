package com.guojunjie.springbootblog.util;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @author guojunjie
 */
public class JWTUtil {

    /**
     * 私钥盐
     */
    final static String TOKEN_SECRET = "guojunjie";

    /**
     * 过期时间,发布后修改为2小时
     */
    final static long TOKEN_EXP = 1000 * 60 * 60 * 2;

    /**
     * 开发时验证token失效，设置为1分钟
     */
//    final static long TOKEN_EXP = 1000 * 60;

    /**
     * token失效或错误
     */
    final static int ERROR_TOKEN_CODE = 401;

    /**
     * 生成token
     *
     * @param userName
     * @return
     */
    public static String createToken(String userName) {

        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + TOKEN_EXP);
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("userName", userName)
                    .withExpiresAt(expiresAt)
                    // 使用了HMAC256加密算法。
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }
    /**
     * 签名验证
     *
     * @param token
     * @return
     */
    public static String verify(String token) {

        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            Claim claim = claims.get("userName");
            String userName = claim.asString();
            System.out.println(userName);
            return userName;
        } catch (Exception e) {
            return null;
        }
    }

    public static HttpServletResponse getFailResponse(HttpServletResponse response,String msg){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        JSONObject json = new JSONObject();
        json.put("msg",msg);
        json.put("code", ERROR_TOKEN_CODE);
        try {
            response.getWriter().append(json.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}