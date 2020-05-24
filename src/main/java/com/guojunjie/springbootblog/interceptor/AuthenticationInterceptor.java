package com.guojunjie.springbootblog.interceptor;

import com.guojunjie.springbootblog.util.JWTUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author guojunjie
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        // 如果没有这一步，取出的token会为null
        // 原因理解：跨域会发两次请求，第一次请求中并没有携带token，第一次请求映射的也不是方法
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        if (token == null) {
            response = JWTUtil.getFailResponse(response, "请先登录");
            return false;
        }
        String userName = JWTUtil.verify(token);
        if (userName != null) {
            return true;
        }
        response = JWTUtil.getFailResponse(response, "登录失效，请重新登录");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}