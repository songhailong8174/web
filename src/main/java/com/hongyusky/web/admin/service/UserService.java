package com.hongyusky.web.admin.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hongyusky.web.admin.mapper.ApplyInfoMapper;
import com.hongyusky.web.admin.mapper.UserMapper;
import com.hongyusky.web.admin.model.*;
import com.hongyusky.web.admin.status.ResultEnum;;
import com.hongyusky.web.admin.status.SysContant;
import com.hongyusky.web.admin.util.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.util.*;


/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 11:28 2019-09-26
 **/
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ApplyInfoMapper applyInfoMapper;
    @Autowired
    private RedisUtils redisUtils;

    public ResultInfo login(String userName, String password, HttpServletResponse response){
        User user = userMapper.loadByMobile(userName);
        if(user == null){
            return ResultInfo.getFailInfo(ResultEnum.LOGIN_USERNAME_ERR);
        }

        if(!user.getPassword().equals(password)){
            return ResultInfo.getFailInfo(ResultEnum.LOGIN_PASSWORD_ERR);
        }

        String token = generateToken(user);
        CookieUtils.setCookie(response, "token", token, -1);
        Map map = new HashMap();
        map.put("token", token);
        map.put("role", user.getRole() == 1 ? "admin" : "user");

        return ResultInfo.getSuccessInfo(map);
    }

    public ResultInfo getAccountInfo (String userId) {
        Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
        reqInfoDatas.put("userId", userId);
        ResponseBean responseBean = HttpUtils.sendHttp(10003, userId, null, reqInfoDatas);
        if (responseBean.isSuccess()) {
            Map<String, Object> map = responseBean.getDataMap();
            List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("list");
            Map<String, Object> currentUser = list.get(0);
            return ResultInfo.getSuccessInfo(currentUser.get("balance"));
        }
        return ResultInfo.getFailInfo(ResultEnum.REMOTE_REQUEST_ERR.getCode(), "获取账户余额失败");
    }

    public ResultInfo logout(HttpServletResponse response){
        CookieUtils.setCookie(response, "token", null, 0);
        return ResultInfo.getSuccessInfo();
    }

    private String generateToken(User user){
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 60 * 60 * 1000;   //有效时间 1小时
        Date end = new Date(currentTime);
        String token = JWT.create()
                .withAudience(user.getUserid())
                .withIssuedAt(start)
                .withExpiresAt(end)
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }

    public ResultInfo checkUserName (String userName) {
        boolean flag = this.checkRemoteUserName(userName);
        return flag ? ResultInfo.getFailInfo(ResultEnum.USER_EXISTS) : ResultInfo.getSuccessInfo();
    }

    public ResultInfo addApplyInfo (ApplyInfo applyInfo) {
        // 校验数据完整性
        // 插入数据
        // 发送注册短信，使用系统默认账户
        if(!redisUtils.hasKey(applyInfo.getMobile())){
            return ResultInfo.getFailInfo(ResultEnum.USER_CODE_ERR);
        }
        String code = String.valueOf(redisUtils.get(applyInfo.getMobile()));
        if(!code.equals(applyInfo.getCode())){
            return ResultInfo.getFailInfo(ResultEnum.USER_CODE_ERR);
        }
        applyInfo.setCreatetime(new Date());
        applyInfoMapper.insert(applyInfo);
        this.sendNotice(applyInfo);
        return ResultInfo.getSuccessInfo();
    }

    private void sendNotice(ApplyInfo applyInfo) {
        User sysUser = userMapper.loadSysUser();
        String userId = "";
        if (sysUser != null) {
            userId = sysUser.getUserid();
        }
        if(StringUtils.isEmpty(userId)) {
            return;
        }
        Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
        reqInfoDatas.put("mobiles", "13082550559");
        reqInfoDatas.put("content", "【南京睿宏无限】官网有新客户提交了服务申请，联系人为" + applyInfo.getName() + "，手机号码为" + applyInfo.getMobile() + "，邮箱为" + applyInfo.getEmail() + "，申请服务为" + applyInfo.getServices() + "，请尽快处理。 ");
        ResponseBean responseBean = HttpUtils.sendHttp(30001, userId, null, reqInfoDatas);
        System.out.println(responseBean);
    }

    public ResultInfo getImageCode (String preId) {
        try {
            if (StringUtils.isNotEmpty(preId) && redisUtils.hasKey(preId)) {
                redisUtils.del(preId);
            }
            Map<String, String> map = CodeUtil.generateCodeAndPic();
            Map<String, String> codeMap = new HashMap<String, String>();
            codeMap.put("id", UUID.randomUUID().toString());
            codeMap.put("img", map.get("pic"));
            redisUtils.set(codeMap.get("id"), map.get("code"), 300);
            return ResultInfo.getSuccessInfo(codeMap);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultInfo.getFailInfo(ResultEnum.GET_IMG_CODE);
        }
    }

    /**
     *
     * @param user
     */
    public ResultInfo addUser(User user){
        System.out.println("begin....");
        boolean flag = this.checkRemoteUserName(user.getUsername());
        System.out.println("flag=" + flag);
        if(flag){
            return ResultInfo.getFailInfo(ResultEnum.USER_EXISTS);
        }
        User dbUser = userMapper.loadByUserName(user.getUsername());
        if(dbUser != null){
            return ResultInfo.getFailInfo(ResultEnum.USER_EXISTS);
        }
        dbUser = userMapper.loadByMobile(user.getMobile());
        if(dbUser != null){
            return ResultInfo.getFailInfo(ResultEnum.USER_MOBILE_EXISTS);
        }
        if(!redisUtils.hasKey(user.getMobile())){
            return ResultInfo.getFailInfo(ResultEnum.USER_CODE_ERR);
        }
        dbUser = null;
        String code = String.valueOf(redisUtils.get(user.getMobile()));
        if(!code.equals(user.getRealname())){
            return ResultInfo.getFailInfo(ResultEnum.USER_CODE_ERR);
        }
        user.setRealname(null);
        System.out.println("用户名检测：" + flag);
        user.setUserid(UUID.randomUUID().toString().replace("-", ""));
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        user.setRole(0);
        user.setStatus(10);
        user.setCreatedate(new Date());
        userMapper.insert(user);
        System.out.println("end.....");
        return ResultInfo.getSuccessInfo();
    }

    private boolean checkRemoteUserName(String userName){
        Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
        reqInfoDatas.put("username", userName);
        ResponseBean responseBean = HttpUtils.sendHttp(10004, null, null, reqInfoDatas);
        System.out.println(responseBean);
        if(responseBean.isSuccess()){
            System.out.println("返回结果：" + responseBean);
            boolean flag = (Boolean) responseBean.getDataMap().get("isSuccess");
            return flag;
        }
        return false;
    }

    public ResultInfo modifyPassword(User user){
        if(!redisUtils.hasKey(user.getMobile())){
            return ResultInfo.getFailInfo(ResultEnum.USER_CODE_ERR);
        }
        String code = String.valueOf(redisUtils.get(user.getMobile()));
        if(!code.equals(user.getRealname())){
            return ResultInfo.getFailInfo(ResultEnum.USER_CODE_ERR);
        }
        User dbUser = userMapper.loadByMobile(user.getMobile());
        if(dbUser == null){
            return ResultInfo.getFailInfo(ResultEnum.USER_NOT_EXISTS);
        }
        user.setRealname(null);
        user.setCompany(null);
        user.setMobile(null);
        user.setUserid(dbUser.getUserid());
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        user.setModifydate(new Date());
        userMapper.update(user);
        if (dbUser.getAuthstatus() != null && dbUser.getAuthstatus() == 1) {
            this.modifyRemotePassword(user, dbUser.getPassword());
        }
        return ResultInfo.getSuccessInfo();
    }

    public ResultInfo modifyPassword(User user, String userId){
        User dbUser = userMapper.load(userId);
        if(dbUser == null){
            return ResultInfo.getFailInfo(ResultEnum.USER_NOT_EXISTS);
        }
        if(!dbUser.getPassword().equals(user.getPassword())){
            return ResultInfo.getFailInfo(ResultEnum.USER_OLD_PASSWORD_ERR);
        }
        user.setPassword(user.getRealname());
        user.setRealname(null);
        user.setCompany(null);
        user.setModifydate(new Date());
        user.setUserid(userId);
        userMapper.update(user);
        if (dbUser.getAuthstatus() != null && dbUser.getAuthstatus() == 1) {
            this.modifyRemotePassword(user, dbUser.getPassword());
        }
        return ResultInfo.getSuccessInfo();
    }

    private ResultInfo modifyRemotePassword(User user, String oldPass){
        Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
        reqInfoDatas.put("oldPassword", oldPass);
        reqInfoDatas.put("userId",user.getUserid());
        reqInfoDatas.put("newPassword",DigestUtils.md5Hex(user.getPassword()));
        ResponseBean responseBean = HttpUtils.sendHttp(10005, user.getUserid(), null, reqInfoDatas);
        System.out.println(responseBean);
        if(responseBean.isSuccess()){
            boolean flag = (Boolean) responseBean.getDataMap().get("isSuccess");
            if(flag){
                return ResultInfo.getSuccessInfo();
            }
            String msg = String.valueOf(responseBean.getDataMap().get("msg"));
            return ResultInfo.getFailInfo(ResultEnum.REMOTE_REQUEST_ERR.getCode(), msg);
        }
        return ResultInfo.getFailInfo();
    }

    public ResultInfo modifyMobile(User user) {
        if(!redisUtils.hasKey(user.getMobile())){
            return ResultInfo.getFailInfo(ResultEnum.USER_CODE_ERR);
        }
        String code = String.valueOf(redisUtils.get(user.getMobile()));
        if(!code.equals(user.getRealname())){
            return ResultInfo.getFailInfo(ResultEnum.USER_CODE_ERR);
        }
        User dbUser = userMapper.load(user.getUserid());
        if(!dbUser.getMobile().equals(user.getMobile())){
            return ResultInfo.getFailInfo(ResultEnum.USER_MOBILE_ERROR);
        }
        if(userMapper.loadByMobile(user.getCompany()) != null){
            return ResultInfo.getFailInfo(ResultEnum.USER_MOBILE_EXISTS);
        }
        user.setMobile(user.getCompany());
        user.setModifydate(new Date());
        userMapper.update(user);
        if (dbUser.getAuthstatus() != null && dbUser.getAuthstatus() == 1) {
            return modifyRemoteMobile(user.getUserid(), user.getMobile());
        } else {
            return ResultInfo.getSuccessInfo();
        }
    }

    private ResultInfo modifyRemoteMobile(String userId, String mobile){
        Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
        reqInfoDatas.put("userId",userId);
        reqInfoDatas.put("mobile", mobile);
        ResponseBean responseBean = HttpUtils.sendHttp(10006, userId, null, reqInfoDatas);
        if(responseBean.isSuccess()){
            Map<String, Object> dataMap = responseBean.getDataMap();
            boolean flag = (Boolean) dataMap.get("isSuccess");
            if(flag){
                return ResultInfo.getSuccessInfo();
            }
            return ResultInfo.getFailInfo(ResultEnum.REMOTE_REQUEST_ERR.getCode(), dataMap.get("msg").toString());
        }

        return ResultInfo.getFailInfo(ResultEnum.REMOTE_REQUEST_ERR.getCode(), "resultCode:" + responseBean.getResultCode() + "," + responseBean.getResultMsg());
    }



    /**
     *
     */
    public ResultInfo modifyBaseInfo(String email, String street, String userId){
        User user = new User();
        user.setUserid(userId);
        user.setEmail(email);
        user.setAddress(street);
        user.setModifydate(new Date());
        userMapper.update(user);
        return ResultInfo.getSuccessInfo();
    }

    public ResultInfo getValidCode(String userId, String mobile) {
        if(StringUtils.isEmpty(mobile)){
            return ResultInfo.getFailInfo(ResultEnum.USER_MOBILE_EMPTY);
        }
        if(!StringUtil.isMobile(mobile)){
            return ResultInfo.getFailInfo(ResultEnum.USER_MOBILE_ERR);
        }
        User user = userMapper.load(userId);
        if (user.getAuthstatus() != 1) {
            User sysUser = userMapper.loadSysUser();
            if (sysUser != null) {
                userId = sysUser.getUserid();
            }
        }
        String code = StringUtil.getValidCode();
        boolean flag = redisUtils.set(mobile, code, 300);
        Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
//		reqInfoDatas.put("userId",userId);
        reqInfoDatas.put("mobiles", mobile);
        reqInfoDatas.put("content", "【南京睿宏无限】您的短信验证码为： " + code);
//		reqInfoDatas.put("setTime", "2019-09-18 15:00:00");
//		reqInfoDatas.put("srcNum", "123456");
        ResponseBean responseBean = HttpUtils.sendHttp(30001, userId, null, reqInfoDatas);
        System.out.println(responseBean);
        if(responseBean.isSuccess()){
            Map dataMap = responseBean.getDataMap();
            if((Boolean) dataMap.get("isSuccess")){
                return ResultInfo.getSuccessInfo();
            }
            return ResultInfo.getFailInfo(ResultEnum.REMOTE_REQUEST_ERR.getCode(), dataMap.get("msg").toString());
        }
        return ResultInfo.getFailInfo(333, "获取验证码错误");
    }

    public ResultInfo getValidCode(String userId, String mobile, String id, String imgCode) {

        if(StringUtils.isEmpty(id) || StringUtils.isEmpty(imgCode)) {
            return ResultInfo.getFailInfo(ResultEnum.IMG_CODE_EMPTY);
        }

        if(StringUtils.isEmpty(mobile)){
            return ResultInfo.getFailInfo(ResultEnum.USER_MOBILE_EMPTY);
        }
        if(!StringUtil.isMobile(mobile)){
            return ResultInfo.getFailInfo(ResultEnum.USER_MOBILE_ERR);
        }
        User user = userMapper.load(userId);
        if (user.getAuthstatus() != 1) {
            User sysUser = userMapper.loadSysUser();
            if (sysUser != null) {
                userId = sysUser.getUserid();
            }
        }
        String code = StringUtil.getValidCode();
        boolean flag = redisUtils.set(mobile, code, 300);
        Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
//		reqInfoDatas.put("userId",userId);
        reqInfoDatas.put("mobiles", mobile);
        reqInfoDatas.put("content", "【南京睿宏无限】您的短信验证码为： " + code);
//		reqInfoDatas.put("setTime", "2019-09-18 15:00:00");
//		reqInfoDatas.put("srcNum", "123456");
        ResponseBean responseBean = HttpUtils.sendHttp(30001, userId, null, reqInfoDatas);
        System.out.println(responseBean);
        if(responseBean.isSuccess()){
            Map dataMap = responseBean.getDataMap();
            if((Boolean) dataMap.get("isSuccess")){
                return ResultInfo.getSuccessInfo();
            }
            return ResultInfo.getFailInfo(ResultEnum.REMOTE_REQUEST_ERR.getCode(), dataMap.get("msg").toString());
        }
        return ResultInfo.getFailInfo(333, "获取验证码错误");
    }

    public ResultInfo loadUserById(String userId) {
        User user = userMapper.load(userId);
        if(user == null){
            return ResultInfo.getFailInfo(ResultEnum.USER_NOT_EXISTS);
        }
        return ResultInfo.getSuccessInfo(user);
    }

    public User loadUser(String userId){
       return userMapper.load(userId);
    }

    public ResultInfo auth(User user){
        user.setModifydate(new Date());
        userMapper.update(user);
        return ResultInfo.getSuccessInfo();
    }

    public ResultInfo getUsersList (int pageNo, int pageSize, String username, String mobile, String company, String authstatus) {
        if(pageNo == 1) pageNo = 0;
        List<User> list = userMapper.pageList(pageNo, pageSize, username, mobile, company, authstatus);
        int rowCount = userMapper.pageListCount(pageNo, pageSize, username, mobile, company, authstatus);
        return ResultInfo.getSuccessInfo(PageData.buildResult(list, rowCount, pageNo, pageSize));
    }

    public ResultInfo appove (String userId) {
        User user = userMapper.load(userId);
        Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
        reqInfoDatas.put("userId",user.getUserid());
        reqInfoDatas.put("mobile", user.getMobile());
        reqInfoDatas.put("username", user.getUsername());
        reqInfoDatas.put("password", user.getPassword());
        reqInfoDatas.put("realName", user.getContact());
        reqInfoDatas.put("smsPrice",0.01);
        reqInfoDatas.put("email", user.getEmail());
        reqInfoDatas.put("moneyAccount", user.getCompany());
        reqInfoDatas.put("accountNum", user.getAccountnum());
        reqInfoDatas.put("company", user.getCompany());
        System.out.println("参数：" + reqInfoDatas);
        ResponseBean sendHttp = HttpUtils.sendHttp(10002, null, null, reqInfoDatas);
        System.out.println(sendHttp);
        if(sendHttp.isSuccess()){
            Map<String, Object> dataMap = sendHttp.getDataMap();
            boolean flag = (Boolean) dataMap.get("isSuccess");
            if(flag){
                user.setAuthstatus(1);
                userMapper.update(user);
                return ResultInfo.getSuccessInfo();
            }
            return ResultInfo.getFailInfo(ResultEnum.REMOTE_REQUEST_ERR.getCode(), dataMap.get("msg").toString());
        } else {
            return ResultInfo.getFailInfo(sendHttp.getResultCode(), sendHttp.getResultMsg());
        }
    }

}
