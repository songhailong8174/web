package com.hongyusky.web.admin.service;

import com.hongyusky.web.admin.model.*;
import com.hongyusky.web.admin.status.ResultEnum;
import com.hongyusky.web.admin.util.HttpUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 11:24 2019-10-08
 **/
@Service
public class SmsService {

    public ResultInfo getTemplates(String userId, int pageIndex, int pageSize, String startDay, String endDay, String status) {
        Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
        reqInfoDatas.put("userId",userId);
        Page page = new Page(pageIndex, pageSize);
        if(StringUtils.isNotEmpty(startDay) && StringUtils.isNotEmpty(endDay)){
            reqInfoDatas.put("startDay", startDay);
            reqInfoDatas.put("endDay", endDay);
        }
        if(StringUtils.isNotEmpty(status)){
            reqInfoDatas.put("status", Integer.valueOf(status));
        }
        ResponseBean responseBean = HttpUtils.sendHttp(20002, userId, page, reqInfoDatas);
        System.out.println(responseBean);
        if(responseBean.isSuccess()){
            return ResultInfo.getSuccessInfo(PageData.buildResult(responseBean.getDataMap()));
        }
        return ResultInfo.getFailInfo(ResultEnum.REMOTE_REQUEST_ERR.getCode(), responseBean.getResultMsg());
    }

    public ResultInfo addTemplate(Template template, String userId) {
        Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
        reqInfoDatas.put("userId",userId);
        reqInfoDatas.put("type", template.getType());
        reqInfoDatas.put("signature", template.getSignature());
        reqInfoDatas.put("title", template.getTitle());
        reqInfoDatas.put("content", template.getContent());
        ResponseBean responseBean = HttpUtils.sendHttp(20001, userId, null, reqInfoDatas);
        System.out.println(responseBean);
        if(responseBean.isSuccess()){
            Map dataMap = responseBean.getDataMap();
            if((Boolean) dataMap.get("isSuccess")){
                return ResultInfo.getSuccessInfo();
            }
            return ResultInfo.getFailInfo(ResultEnum.REMOTE_REQUEST_ERR.getCode(), dataMap.get("msg").toString());
        }
        return ResultInfo.getFailInfo(ResultEnum.REMOTE_REQUEST_ERR.getCode(), responseBean.getResultMsg());
    }

    public ResultInfo deleteTemplate(String userId, String templateId) {
        Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
        reqInfoDatas.put("userId", userId);
        reqInfoDatas.put("templateId", templateId);
        ResponseBean responseBean = HttpUtils.sendHttp(20003, userId, null, reqInfoDatas);
        System.out.println(responseBean);
        if(responseBean.isSuccess()){
            Map dataMap = responseBean.getDataMap();
            if((Boolean) dataMap.get("isSuccess")){
                return ResultInfo.getSuccessInfo();
            }
            return ResultInfo.getFailInfo(ResultEnum.REMOTE_REQUEST_ERR.getCode(), dataMap.get("msg").toString());
        }
        return ResultInfo.getFailInfo(ResultEnum.REMOTE_REQUEST_ERR.getCode(), responseBean.getResultMsg());
    }
}
