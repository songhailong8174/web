package com.hongyusky.web.admin.config;

import com.hongyusky.web.admin.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 23:29 2019-10-08
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
        .addPathPatterns("/**");  // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
    }
}
