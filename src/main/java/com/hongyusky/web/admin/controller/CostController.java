package com.hongyusky.web.admin.controller;

import com.hongyusky.web.admin.model.ResultInfo;
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

    @RequestMapping(value = "/rechage", method = RequestMethod.GET)
    public ResultInfo rechageList(){
        return ResultInfo.getSuccessInfo();
    }

    @RequestMapping(value = "/bill", method = RequestMethod.GET)
    public ResultInfo billList(){
        return ResultInfo.getSuccessInfo();
    }
}
