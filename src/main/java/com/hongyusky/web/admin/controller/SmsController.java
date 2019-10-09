package com.hongyusky.web.admin.controller;

import com.hongyusky.web.admin.annotation.UserLoginToken;
import com.hongyusky.web.admin.model.ResultInfo;
import com.hongyusky.web.admin.model.Template;
import com.hongyusky.web.admin.service.SmsService;
import com.hongyusky.web.admin.status.ResultEnum;
import com.hongyusky.web.admin.util.StringUtil;
import com.hongyusky.web.admin.util.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 21:07 2019-09-25
 **/
@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    SmsService smsService;

    @RequestMapping(value = "/template", method = RequestMethod.POST)
    @UserLoginToken
    public ResultInfo addTemplate(Template template){
        String userId = TokenUtils.getTokenUserId();
        if(userId == null){
            return ResultInfo.getFailInfo(ResultEnum.NEED_LOGIN);
        }
        return smsService.addTemplate(template, userId);
    }

    @RequestMapping(value = "/template/delete", method = RequestMethod.GET)
    @UserLoginToken
    public ResultInfo deleteTemplate(String id){
        String userId = TokenUtils.getTokenUserId();
        if(userId == null){
            return ResultInfo.getFailInfo(ResultEnum.NEED_LOGIN);
        }
        if(StringUtils.isEmpty(id)) {
            return ResultInfo.getFailInfo(ResultEnum.SMS_TMPLATE_EMPTY);
        }
        return smsService.deleteTemplate(userId, id);
    }

    @RequestMapping(value = "/template", method = RequestMethod.GET)
    @UserLoginToken
    public ResultInfo loadTemplate(String currentPage, String pageSize, String startDay, String endDay, String status)
    {
        String userId = TokenUtils.getTokenUserId();
        if(userId == null){
            return ResultInfo.getFailInfo(ResultEnum.NEED_LOGIN);
        }
        int pageIndex = StringUtils.isNotEmpty(currentPage) ? Integer.valueOf(currentPage) : 1;
        int _pageSize = StringUtils.isNotEmpty(pageSize) ? Integer.valueOf(pageSize) : 10;
        return smsService.getTemplates(userId, pageIndex, _pageSize, startDay, endDay, status);
    }

    @RequestMapping(value = "/template/notice", method = RequestMethod.POST)
    public ResultInfo approvalNotice() {
        return ResultInfo.getSuccessInfo();
    }


}
