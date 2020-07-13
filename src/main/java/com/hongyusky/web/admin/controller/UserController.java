package com.hongyusky.web.admin.controller;

import com.hongyusky.web.admin.annotation.UserLoginToken;
import com.hongyusky.web.admin.model.ApplyInfo;
import com.hongyusky.web.admin.model.ResultInfo;
import com.hongyusky.web.admin.model.User;
import com.hongyusky.web.admin.service.UserService;
import com.hongyusky.web.admin.status.ResultEnum;
import com.hongyusky.web.admin.status.SysContant;
import com.hongyusky.web.admin.util.QrCodeUtils;
import com.hongyusky.web.admin.util.StringUtil;
import com.hongyusky.web.admin.util.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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

    @GetMapping(value = "/logout")
    public ResultInfo logout(HttpServletResponse response){
        return userService.logout(response);
    }

    @GetMapping(value = "/currentUser")
    @UserLoginToken
    public ResultInfo currentUser() {
        String userId = TokenUtils.getTokenUserId();
        if(userId == null){
            return ResultInfo.getFailInfo(ResultEnum.NEED_LOGIN);
        }
        return userService.loadUserById(userId);
    }

    @RequestMapping(value = "/validCode", method = RequestMethod.GET)
    public ResultInfo sendValidCode(String mobile) {
        //参数非空校验
        //手机号格式校验
        if(StringUtils.isEmpty(mobile)){
            return ResultInfo.getFailInfo(ResultEnum.USER_MOBILE_EMPTY);
        }
        if(!StringUtil.isMobile(mobile)){
            return ResultInfo.getFailInfo(ResultEnum.USER_MOBILE_ERR);
        }
        String userId = TokenUtils.getTokenUserId();
        if (StringUtils.isEmpty(userId)) {
            userId = SysContant.DEFAULT_USER;
        }
        return userService.getValidCode(userId, mobile);
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public ResultInfo checkUserName(String userName) {
        //参数非空校验
        //手机号格式校验
        if(StringUtils.isEmpty(userName)){
            ResultInfo.getFailInfo(ResultEnum.USER_NAME_EMPTY);
        }
        return userService.checkUserName(userName);
    }

    @GetMapping(value = "/getImg")
    public ResultInfo getImg(String id) {
        return userService.getImageCode(id);
    }

    @RequestMapping(value = "/sendCode", method = RequestMethod.GET)
    public ResultInfo sendCode(String mobile, String code, String id) {
        //参数非空校验
        //手机号格式校验
        if(StringUtils.isEmpty(id) || StringUtils.isEmpty(code)) {
            return ResultInfo.getFailInfo(ResultEnum.IMG_CODE_EMPTY);
        }
        if(StringUtils.isEmpty(mobile)){
            return ResultInfo.getFailInfo(ResultEnum.USER_MOBILE_EMPTY);
        }
        if(!StringUtil.isMobile(mobile)){
            return ResultInfo.getFailInfo(ResultEnum.USER_MOBILE_ERR);
        }
        String userId = TokenUtils.getTokenUserId();
        if (StringUtils.isEmpty(userId)) {
            userId = SysContant.DEFAULT_USER;
        }
        return userService.getValidCode(userId, mobile, id, code);
    }


    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public ResultInfo reg(ApplyInfo applyInfo) {
        // 校验参数完整性
        return userService.addApplyInfo(applyInfo);
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
        if(StringUtils.isEmpty(user.getContact())) {
            return ResultInfo.getFailInfo(ResultEnum.USER_CONTACT_EMPTY);
        }
        if(StringUtils.isEmpty(user.getEmail())) {
            return ResultInfo.getFailInfo(ResultEnum.USER_INFO_EMAIL_EMPTY);
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
    public ResultInfo modifyPass(User user) {
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

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResultInfo getAccountInfo() {
        String userId = TokenUtils.getTokenUserId();
        if(userId == null){
            return ResultInfo.getFailInfo(ResultEnum.NEED_LOGIN);
        }
        return userService.getAccountInfo(userId);
    }

    @RequestMapping(value = "/user/modify", method = RequestMethod.POST)
    @UserLoginToken
    public ResultInfo modifyUser(String email, String street) {
        String userId = TokenUtils.getTokenUserId();
        if(userId == null){
            return ResultInfo.getFailInfo(ResultEnum.NEED_LOGIN);
        }
        if(StringUtils.isEmpty(email)){
            return ResultInfo.getFailInfo(ResultEnum.USER_INFO_EMAIL_EMPTY);
        }
        if(StringUtils.isEmpty(street)){
            return ResultInfo.getFailInfo(ResultEnum.USER_INFO_STREET_EMPTY);
        }
        return userService.modifyBaseInfo(email, street, userId);
    }

    @RequestMapping(value = "/user/modifyPassword", method = RequestMethod.POST)
    @UserLoginToken
    public ResultInfo modifyPassword(User user){
        String userId = TokenUtils.getTokenUserId();
        if(userId == null){
            return ResultInfo.getFailInfo(ResultEnum.NEED_LOGIN);
        }
        if(StringUtils.isEmpty(user.getPassword())){
            return ResultInfo.getFailInfo(ResultEnum.USER_OLD_PASSWORD_EMPTY);
        }
        if(StringUtils.isEmpty(user.getRealname())){
            return ResultInfo.getFailInfo(ResultEnum.USER_NEW_PASSWORD_EMPTY);
        }
        if(StringUtils.isEmpty(user.getCompany())){
            return ResultInfo.getFailInfo(ResultEnum.USER_REPASSWORD_EMPTY);
        }
        if(!user.getRealname().equals(user.getCompany())){
            return ResultInfo.getFailInfo(ResultEnum.USER_PASSWORD_REPASSWORD_NOT_EQUALS);
        }
        return userService.modifyPassword(user, userId);
    }

    @RequestMapping(value = "/user/modifyMobile", method = RequestMethod.POST)
    @UserLoginToken
    public ResultInfo modifyMobile(User user) {
        String userId = TokenUtils.getTokenUserId();
        if(userId == null){
            return ResultInfo.getFailInfo(ResultEnum.NEED_LOGIN);
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
        if(StringUtils.isEmpty(user.getCompany())){
            return ResultInfo.getFailInfo(ResultEnum.USER_NEW_MOBILE_EMPTY);
        }
        if(!StringUtil.isMobile(user.getCompany())){
            return ResultInfo.getFailInfo(ResultEnum.USER_NEW_MOBILE_ERR);
        }
        if(user.getMobile().equals(user.getCompany())){
            return ResultInfo.getFailInfo(ResultEnum.USER_MOBILE_EQUALS);
        }
        user.setUserid(userId);
        return userService.modifyMobile(user);
    }

    @RequestMapping(value = "/user/auth", method = RequestMethod.POST)
    @UserLoginToken
    public ResultInfo auth(User user) {
        String userId = TokenUtils.getTokenUserId();
        if(userId == null){
            return ResultInfo.getFailInfo(ResultEnum.NEED_LOGIN);
        }
        if(StringUtils.isEmpty(user.getCompany())){
            return ResultInfo.getFailInfo(ResultEnum.USER_COMPANY_EMPTY);
        }
        if(StringUtils.isEmpty(user.getTaxno())){
            return ResultInfo.getFailInfo(ResultEnum.USER_TAXNO_EMPTY);
        }
        if(StringUtils.isEmpty(user.getAccountnum())){
            return ResultInfo.getFailInfo(ResultEnum.USER_ACCOUNT_EMPTY);
        }
        if(StringUtils.isEmpty(user.getProvince())){
            return ResultInfo.getFailInfo(ResultEnum.USER_PROVINCE_EMPTY);
        }
        if(StringUtils.isEmpty(user.getCity())){
            return ResultInfo.getFailInfo(ResultEnum.USER_CITY_EMPTY);
        }
        if(StringUtils.isEmpty(user.getAddress())){
            return ResultInfo.getFailInfo(ResultEnum.USER_ADDRESS_EMPTY);
        }
        if(StringUtils.isEmpty(user.getContact())){
            return ResultInfo.getFailInfo(ResultEnum.USER_CONTACT_EMPTY);
        }
        if(StringUtils.isEmpty(user.getPhone())){
            return ResultInfo.getFailInfo(ResultEnum.USER_PHONE_EMPTY);
        }
        if(!StringUtil.validPhoneNum("2", user.getPhone())){
            return ResultInfo.getFailInfo(ResultEnum.USER_PHONE_ERR);
        }
        user.setUserid(userId);
        return userService.auth(user);
    }

    @GetMapping(value = "/list")
    @UserLoginToken
    public ResultInfo getusersList(String currentPage, String pageSize, String username, String mobile, String company, String authstatus) {
        String userId = TokenUtils.getTokenUserId();
        if(userId == null){
            return ResultInfo.getFailInfo(ResultEnum.NEED_LOGIN);
        }
        int pageIndex = StringUtils.isNotEmpty(currentPage) ? Integer.valueOf(currentPage) : 0;
        int _pageSize = StringUtils.isNotEmpty(pageSize) ? Integer.valueOf(pageSize) : 10;
        return userService.getUsersList(pageIndex, _pageSize, username, mobile, company, authstatus);
    }

    @PostMapping(value = "/appove")
    @UserLoginToken
    public ResultInfo appove (String id) {
        String userId = TokenUtils.getTokenUserId();
        if(userId == null){
            return ResultInfo.getFailInfo(ResultEnum.NEED_LOGIN);
        }
        return userService.appove(id);
    }

    @RequestMapping(value = "/user/qrcode", method = RequestMethod.GET)
    public ResultInfo getQrCode() {
        String base64 = null;
        String userId = TokenUtils.getTokenUserId();
        if(userId == null){
            return ResultInfo.getFailInfo(ResultEnum.NEED_LOGIN);
        }
        try {
            base64 = QrCodeUtils.encode("https://www.baidu.com", null, false);
            return ResultInfo.getSuccessInfo(base64);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.getFailInfo();
        }

    }
}

