package com.hongyusky.web.admin.controller;

import com.hongyusky.web.admin.model.ResultInfo;
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

    @RequestMapping(value = "/sedingCount", method = RequestMethod.GET)
    public ResultInfo sendingCount(){
        return ResultInfo.getSuccessInfo();
    }

    @RequestMapping(value = "/history/sending", method = RequestMethod.GET)
    public ResultInfo sendingHistory(){
        return ResultInfo.getSuccessInfo();
    }

    @RequestMapping(value = "/history/receive", method = RequestMethod.GET)
    public ResultInfo receiveHistory(){
        return ResultInfo.getSuccessInfo();
    }
}
