package com.hongyusky.web.admin.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.hongyusky.web.admin.annotation.PassToken;
import com.hongyusky.web.admin.annotation.UserLoginToken;
import com.hongyusky.web.admin.exception.SysException;
import com.hongyusky.web.admin.model.User;
import com.hongyusky.web.admin.service.UserService;
import com.hongyusky.web.admin.status.ResultEnum;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 23:33 2019-10-08
 **/
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        String token = request.getHeader("x-auth-token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
//                    throw new HttpResponseException(401, ResultEnum.NEED_LOGIN.getMsg());
                    throw new SysException(ResultEnum.NEED_LOGIN);
//                    throw new RuntimeException("无token，请重新登录");
                }
                // 获取 token 中的 user id
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new SysException(ResultEnum.NEED_LOGIN);
                }
                User user = userService.loadUser(userId);
                if (user == null) {
                    throw new SysException(ResultEnum.NEED_LOGIN);
//                    throw new SysException(ResultEnum.USER_NOT_EXISTS);
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new SysException(ResultEnum.NEED_LOGIN);
//                    throw new RuntimeException("401");
                }
                return true;
            }
        }
        return true;
    }
}
