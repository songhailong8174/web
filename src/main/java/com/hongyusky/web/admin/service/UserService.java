package com.hongyusky.web.admin.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hongyusky.web.admin.mapper.UserMapper;
import com.hongyusky.web.admin.model.ResponseBean;
import com.hongyusky.web.admin.model.ResultInfo;
import com.hongyusky.web.admin.model.User;
import com.hongyusky.web.admin.status.ResultEnum;;
import com.hongyusky.web.admin.util.CookieUtils;
import com.hongyusky.web.admin.util.HttpUtils;
import com.hongyusky.web.admin.util.RedisUtils;
import com.hongyusky.web.admin.util.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


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
        CookieUtils.setCookie(response, "token", token);

        return ResultInfo.getSuccessInfo(token);
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
        userMapper.update(user);
        this.modifyRemotePassword(user, dbUser.getPassword());
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



    /**
     *
     */
    public void deleteUser(){

    }

    public ResultInfo getValidCode(String mobile) {
        if(StringUtils.isEmpty(mobile)){
            return ResultInfo.getFailInfo(ResultEnum.USER_MOBILE_EMPTY);
        }
        if(!StringUtil.isMobile(mobile)){
            return ResultInfo.getFailInfo(ResultEnum.USER_MOBILE_ERR);
        }
        String code = StringUtil.getValidCode();
        boolean flag = redisUtils.set(mobile, code, 300);
        return flag ? ResultInfo.getSuccessInfo(code) : ResultInfo.getFailInfo();
    }

    public ResultInfo loadUserById(String userId) {
        User user = userMapper.load(userId);
        if(user == null){
            return ResultInfo.getFailInfo(ResultEnum.USER_NOT_EXISTS);
        }
        return ResultInfo.getSuccessInfo(user);
    }
}
