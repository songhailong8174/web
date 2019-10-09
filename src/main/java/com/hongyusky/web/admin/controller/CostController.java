package com.hongyusky.web.admin.controller;

import com.hongyusky.web.admin.annotation.UserLoginToken;
import com.hongyusky.web.admin.model.ResultInfo;
import com.hongyusky.web.admin.service.CostService;
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
 * @Date Created in 08:10 2019-09-26
 **/
@RestController
@RequestMapping(value = "/cost")
@ResponseBody
public class CostController {

    @Autowired
    CostService costService;

    @RequestMapping(value = "/rechage", method = RequestMethod.GET)
    @UserLoginToken
    public ResultInfo rechageList(String currentPage, String pageSize, String startDay, String endDay){
        String userId = TokenUtils.getTokenUserId();
        if(userId == null){
            return ResultInfo.getFailInfo(ResultEnum.NEED_LOGIN);
        }
        int pageIndex = StringUtils.isNotEmpty(currentPage) ? Integer.valueOf(currentPage) : 1;
        int _pageSize = StringUtils.isNotEmpty(pageSize) ? Integer.valueOf(pageSize) : 10;
        return costService.getRechageList(userId, pageIndex, _pageSize, startDay, endDay);
    }

    @RequestMapping(value = "/bill", method = RequestMethod.GET)
    @UserLoginToken
    public ResultInfo billList(String currentPage, String pageSize, String startDay, String endDay){
        String userId = TokenUtils.getTokenUserId();
        if(userId == null){
            return ResultInfo.getFailInfo(ResultEnum.NEED_LOGIN);
        }
        int pageIndex = StringUtils.isNotEmpty(currentPage) ? Integer.valueOf(currentPage) : 1;
        int _pageSize = StringUtils.isNotEmpty(pageSize) ? Integer.valueOf(pageSize) : 10;
        return costService.costDetail(userId, pageIndex, _pageSize, startDay, endDay);
    }
}
