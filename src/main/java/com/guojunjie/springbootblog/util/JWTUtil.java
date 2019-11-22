package com.guojunjie.springbootblog.util;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.guojunjie.springbootblog.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class JWTUtil {

    final static String TOKEN_SECRET = "guojunjie";//私钥盐

    final static long TOKEN_EXP = 1000 * 60 * 60 * 3;//过期时间,发布时修改为2小时

//    final static long TOKEN_EXP = 1000 * 60 * 30;//开发时验证token失效，设置为30分钟

    final static int error_Code = 401;//token失效或错误

    /**
     * 生成token
     *
     * @param user
     * @return
     */
    public static String createToken(User user) {

        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + TOKEN_EXP);
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("userId", user.getUserId())
                    .withExpiresAt(expiresAt)
                    // 使用了HMAC256加密算法。
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;

//        String token="";
//        token= JWT.create().withAudience(String.valueOf(user.getUserId()))
//                .sign(Algorithm.HMAC256(base64EncodedSecretKey));
//        return token;
    }
    /**
     * 签名验证
     *
     * @param token
     * @return
     */
    public static boolean verify(String token) {

        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
//            System.out.println("认证通过：");
//            System.out.println("issuer: " + jwt.getIssuer());
//            System.out.println("userId: " + jwt.getClaim("userId").asString());
//            System.out.println("过期时间：      " + jwt.getExpiresAt());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static HttpServletResponse getFailResponse(HttpServletResponse response,String msg){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        JSONObject json = new JSONObject();
        json.put("msg",msg);
        json.put("resultCode",error_Code);
        try {
            response.getWriter().append(json.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}