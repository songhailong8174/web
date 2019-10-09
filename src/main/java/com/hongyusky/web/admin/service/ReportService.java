package com.hongyusky.web.admin.service;

import com.hongyusky.web.admin.model.Page;
import com.hongyusky.web.admin.model.PageData;
import com.hongyusky.web.admin.model.ResponseBean;
import com.hongyusky.web.admin.model.ResultInfo;
import com.hongyusky.web.admin.status.ResultEnum;
import com.hongyusky.web.admin.util.HttpUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 15:45 2019-10-08
 **/
@Service
public class ReportService {

    public ResultInfo receiveHistory (String userId, int pageIndex, int pageSize, String startTime, String endTime, String content, String smscontent) {
        Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
        reqInfoDatas.put("userId", userId);
        Page page = new Page(pageIndex, pageSize);
        //reqInfoDatas.put("content", "【测试】123");
        if(StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)){
            reqInfoDatas.put("startDay", startTime + " 00：00：00");
            reqInfoDatas.put("endDay", endTime + " 23：59：59");
        }
        if(StringUtils.isNotEmpty(content)){
            reqInfoDatas.put("content", content);
        }
        if(StringUtils.isNotEmpty(smscontent)){
            reqInfoDatas.put("content", smscontent);
        }
        ResponseBean responseBean = HttpUtils.sendHttp(50001, userId, page, reqInfoDatas);
        System.out.println(responseBean);
        if(responseBean.isSuccess()){
            return ResultInfo.getSuccessInfo(PageData.buildResult(responseBean.getDataMap()));
        }
        return ResultInfo.getFailInfo(ResultEnum.REMOTE_REQUEST_ERR.getCode(), responseBean.getResultMsg());
    }

    public ResultInfo sendHistoryNear(String userId,int pageIndex, int pageSize, String startTime, String endTime, String content, String mobiles, String sendState){
        Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
        reqInfoDatas.put("userId",userId);
        Page	page = new Page(pageIndex, pageSize);
        //reqInfoDatas.put("content", "【测试】123");
        reqInfoDatas.put("startTime", startTime + " 00:00:00");
        reqInfoDatas.put("endTime", endTime + " 23:59:59");
        if(StringUtils.isNotEmpty(content)){
            reqInfoDatas.put("content", content);
        }
        if(StringUtils.isNotEmpty(mobiles)){
            reqInfoDatas.put("mobiles", mobiles);
        }
        if(StringUtils.isNotEmpty(sendState)){
            reqInfoDatas.put("sendState", Integer.valueOf(sendState));
        }
//        reqInfoDatas.put("mobiles", "17502515429");
        ResponseBean responseBean = HttpUtils.sendHttp(50002, userId, page, reqInfoDatas);
        System.out.println(responseBean);
        if(responseBean.isSuccess()){
            return ResultInfo.getSuccessInfo(PageData.buildResult(responseBean.getDataMap()));
        }
        return ResultInfo.getFailInfo(ResultEnum.REMOTE_REQUEST_ERR.getCode(), responseBean.getResultMsg());
    }

    public ResultInfo sendHistory(String userId,int pageIndex, int pageSize, String startTime, String content, String mobiles){
        Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
        reqInfoDatas.put("userId",userId);
        Page	page = new Page(pageIndex, pageSize);
//        reqInfoDatas.put("content", "【测试】测试发送");
        reqInfoDatas.put("startTime", startTime + " 00:00:00");
        reqInfoDatas.put("endTime", startTime + " 23:59:59");
        if(StringUtils.isNotEmpty(content)){
            reqInfoDatas.put("content", content);
        }
        if(StringUtils.isNotEmpty(mobiles)){
            reqInfoDatas.put("mobiles", mobiles);
        }
        ResponseBean responseBean = HttpUtils.sendHttp(50003, userId, page, reqInfoDatas);
        System.out.println(responseBean);
        if(responseBean.isSuccess()){
            return ResultInfo.getSuccessInfo(PageData.buildResult(responseBean.getDataMap()));
        }
        return ResultInfo.getFailInfo(ResultEnum.REMOTE_REQUEST_ERR.getCode(), responseBean.getResultMsg());
    }
}
