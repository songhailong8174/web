package com.hongyusky.web.admin.util;

import com.auth0.jwt.JWT;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 22:17 2019-09-28
 **/
public class TokenUtils {

    public static String getTokenUserId() {
        String token = getRequest().getHeader("x-auth-token");// 从 http 请求头中取出 token
        if(StringUtils.isEmpty(token)){
            return null;
        }
        String userId = JWT.decode(token).getAudience().get(0);
        return userId;
    }

    /**
     * 获取request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }
}
