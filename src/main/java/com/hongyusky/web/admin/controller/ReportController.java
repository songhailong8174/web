package com.hongyusky.web.admin.controller;

import com.hongyusky.web.admin.annotation.UserLoginToken;
import com.hongyusky.web.admin.model.ResultInfo;
import com.hongyusky.web.admin.service.ReportService;
import com.hongyusky.web.admin.status.ResultEnum;
import com.hongyusky.web.admin.util.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 08:17 2019-09-26
 **/
@RestController
@RequestMapping("/report")
@ResponseBody
public class ReportController {

    @Autowired
    ReportService reportService;

    @RequestMapping(value = "/sedingCount", method = RequestMethod.GET)
    public ResultInfo sendingCount(){
        return ResultInfo.getSuccessInfo();
    }

    @RequestMapping(value = "/history/near", method = RequestMethod.GET)
    @UserLoginToken
    public ResultInfo sendingHistoryNear(String currentPage, String pageSize, String startDay, String endDay, String content, String mobiles, String sendState){
        String userId = TokenUtils.getTokenUserId();
        if(userId == null){
            return ResultInfo.getFailInfo(ResultEnum.NEED_LOGIN);
        }
        int pageIndex = StringUtils.isNotEmpty(currentPage) ? Integer.valueOf(currentPage) : 1;
        int _pageSize = StringUtils.isNotEmpty(pageSize) ? Integer.valueOf(pageSize) : 10;
        return reportService.sendHistoryNear(userId, pageIndex, _pageSize, startDay, endDay, content, mobiles, sendState);
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    @UserLoginToken
    public ResultInfo sendingHistory(String currentPage, String pageSize, String startDay, String endDay, String content, String mobiles){
        String userId = TokenUtils.getTokenUserId();
        if(userId == null){
            return ResultInfo.getFailInfo(ResultEnum.NEED_LOGIN);
        }
        int pageIndex = StringUtils.isNotEmpty(currentPage) ? Integer.valueOf(currentPage) : 1;
        int _pageSize = StringUtils.isNotEmpty(pageSize) ? Integer.valueOf(pageSize) : 10;
        return reportService.sendHistory(userId, pageIndex, _pageSize, startDay, content, mobiles);
    }

    @RequestMapping(value = "/history/receive", method = RequestMethod.GET)
    @UserLoginToken
    public ResultInfo receiveHistory(String currentPage, String pageSize, String startDay, String endDay, String content, String smscontent){
        String userId = TokenUtils.getTokenUserId();
        if(userId == null){
            return ResultInfo.getFailInfo(ResultEnum.NEED_LOGIN);
        }
        int pageIndex = StringUtils.isNotEmpty(currentPage) ? Integer.valueOf(currentPage) : 1;
        int _pageSize = StringUtils.isNotEmpty(pageSize) ? Integer.valueOf(pageSize) : 10;
        return reportService.receiveHistory(userId, pageIndex, _pageSize, startDay, endDay, content, smscontent);
    }
}
