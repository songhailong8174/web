package com.hongyusky.web.admin.controller;

import com.hongyusky.web.admin.model.ResultInfo;
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

    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    public ResultInfo addSign(String name){
        return ResultInfo.getSuccessInfo();
    }

    @RequestMapping(value = "/sign/{id}", method = RequestMethod.DELETE)
    public ResultInfo deleteSign(@PathVariable("id") Long id){
        return ResultInfo.getSuccessInfo();
    }

    @RequestMapping(value = "/sign/{id}", method = RequestMethod.POST)
    public ResultInfo modifySign(@PathVariable("id") Long id, String name){
        return ResultInfo.getSuccessInfo();
    }

    @RequestMapping(value = "/sign", method = RequestMethod.GET)
    public ResultInfo loadSign(int page, int pageSize){
        return ResultInfo.getSuccessInfo();
    }

    @RequestMapping(value = "/sign/{id}", method = RequestMethod.GET)
    public ResultInfo loadSign(@PathVariable("id") Long id){
        return ResultInfo.getSuccessInfo();
    }

    @RequestMapping(value = "/template", method = RequestMethod.POST)
    public ResultInfo addTemplate(String name){
        return ResultInfo.getSuccessInfo();
    }

    @RequestMapping(value = "/template/{id}", method = RequestMethod.DELETE)
    public ResultInfo deleteTemplate(@PathVariable("id") Long id){
        return ResultInfo.getSuccessInfo();
    }

    @RequestMapping(value = "/template/{id}", method = RequestMethod.POST)
    public ResultInfo modifyTemplate(@PathVariable("id") Long id, String name){
        return ResultInfo.getSuccessInfo();
    }

    @RequestMapping(value = "/template", method = RequestMethod.GET)
    public ResultInfo loadTemplate(int page, int pageSize){
        return ResultInfo.getSuccessInfo();
    }

    @RequestMapping(value = "/template/{id}", method = RequestMethod.GET)
    public ResultInfo loadTemplate(@PathVariable("id") Long id){
        return ResultInfo.getSuccessInfo();
    }

    @RequestMapping(value = "/template/notice", method = RequestMethod.POST)
    public ResultInfo approvalNotice() {
        return ResultInfo.getSuccessInfo();
    }


}
