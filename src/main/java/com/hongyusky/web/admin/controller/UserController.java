package com.hongyusky.web.admin.controller;

import com.hongyusky.web.admin.model.ResultInfo;
import com.hongyusky.web.admin.model.User;
import com.hongyusky.web.admin.service.UserService;
import com.hongyusky.web.admin.status.ResultEnum;
import com.hongyusky.web.admin.util.StringUtil;
import com.hongyusky.web.admin.util.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 13:05 2019-09-24
 **/
@RestController
@RequestMapping(value = "/account")
@ResponseBody
public class UserController {

    @Autowired
    UserService userService;



    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultInfo login(@RequestParam(value = "userName", required = true) String userName,
                            @RequestParam(value = "password", required = true)  String password,
                            HttpServletResponse response) {

        return userService.login(userName, password, response);
    }

    @GetMapping(value = "/currentUser")
    public ResultInfo currentUser() {
        String userId = TokenUtils.getTokenUserId();
        if(userId == null){
            return ResultInfo.getFailInfo(ResultEnum.USER_NOT_EXISTS);
        }
        return userService.loadUserById(userId);
    }

    @RequestMapping(value = "/validCode", method = RequestMethod.GET)
    public ResultInfo sendValidCode(String mobile) {
        //参数非空校验
        //手机号格式校验
        return userService.getValidCode(mobile);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResultInfo register(User user) {
        if(user == null){
            return ResultInfo.getFailInfo(ResultEnum.PARAM_ERR);
        }
        System.out.println(user.getUsername());
        if(StringUtils.isEmpty(user.getUsername())){
            return ResultInfo.getFailInfo(ResultEnum.USER_NAME_EMPTY);
        }
        if(StringUtils.isEmpty(user.getMobile())){
            return ResultInfo.getFailInfo(ResultEnum.USER_MOBILE_EMPTY);
        }
        if(!StringUtil.isMobile(user.getMobile())){
            return ResultInfo.getFailInfo(ResultEnum.USER_MOBILE_ERR);
        }
        if(StringUtils.isEmpty(user.getRealname())){
            return ResultInfo.getFailInfo(ResultEnum.USER_CODE_EMPTY);
        }
        if(StringUtils.isEmpty(user.getPassword())){
            return ResultInfo.getFailInfo(ResultEnum.USER_PASSWORD_EMPTY);
        }
        return userService.addUser(user);
    }

    @RequestMapping(value = "/modifyPass", method = RequestMethod.POST)
    public ResultInfo modifyPassword(User user) {
        if(user == null){
            return ResultInfo.getFailInfo(ResultEnum.PARAM_ERR);
        }
        if(StringUtils.isEmpty(user.getMobile())){
            return ResultInfo.getFailInfo(ResultEnum.USER_MOBILE_EMPTY);
        }
        if(!StringUtil.isMobile(user.getMobile())){
            return ResultInfo.getFailInfo(ResultEnum.USER_MOBILE_ERR);
        }
        if(StringUtils.isEmpty(user.getRealname())){
            return ResultInfo.getFailInfo(ResultEnum.USER_CODE_EMPTY);
        }
        if(StringUtils.isEmpty(user.getPassword())){
            return ResultInfo.getFailInfo(ResultEnum.USER_PASSWORD_EMPTY);
        }
        if(StringUtils.isEmpty(user.getCompany())){
            return ResultInfo.getFailInfo(ResultEnum.USER_REPASSWORD_EMPTY);
        }
        if(!user.getPassword().equals(user.getCompany())){
            return ResultInfo.getFailInfo(ResultEnum.USER_PASSWORD_REPASSWORD_NOT_EQUALS);
        }

        return userService.modifyPassword(user);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResultInfo loadUser(@PathVariable("id") Long id) {
        return ResultInfo.getSuccessInfo();
    }

    @RequestMapping(value = "/user/modify", method = RequestMethod.POST)
    public ResultInfo modifyUser() {
        return ResultInfo.getSuccessInfo();
    }

    @RequestMapping(value = "/user/modifyMobile", method = RequestMethod.POST)
    public ResultInfo modifyMobile() {
        return ResultInfo.getSuccessInfo();
    }

    @RequestMapping(value = "/user/auth", method = RequestMethod.POST)
    public ResultInfo auth() {
        return ResultInfo.getSuccessInfo();
    }
}

