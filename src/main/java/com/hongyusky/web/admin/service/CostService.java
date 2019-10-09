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
 * @Date Created in 10:09 2019-10-08
 **/
@Service
public class CostService {

    public ResultInfo getRechageList(String userId, int pageIndex, int pageSize, String startDay, String endDay){
        Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
        reqInfoDatas.put("userId",userId);
        Page page = new Page(pageIndex, pageSize);
        if(StringUtils.isNotEmpty(startDay) && StringUtils.isNotEmpty(endDay)){
            reqInfoDatas.put("startDay", startDay);
            reqInfoDatas.put("endDay", endDay);
        }
        ResponseBean responseBean = HttpUtils.sendHttp(40001, userId, page, reqInfoDatas);
        System.out.println(responseBean);
        if(responseBean.isSuccess()){
            return ResultInfo.getSuccessInfo(PageData.buildResult(responseBean.getDataMap()));
        }
        return ResultInfo.getFailInfo(ResultEnum.REMOTE_REQUEST_ERR.getCode(), responseBean.getResultMsg());
    }

    public ResultInfo costDetail(String userId, int pageIndex, int pageSize, String startDay, String endDay){
        Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
        reqInfoDatas.put("userId",userId);
        Page page = new Page(pageIndex, pageSize);
        if(StringUtils.isNotEmpty(startDay) && StringUtils.isNotEmpty(endDay)){
            reqInfoDatas.put("startDay", startDay);
            reqInfoDatas.put("endDay", endDay);
        }
        ResponseBean responseBean = HttpUtils.sendHttp(40003, userId, page, reqInfoDatas);
        System.out.println(responseBean);
        if(responseBean.isSuccess()){
            return ResultInfo.getSuccessInfo(PageData.buildResult(responseBean.getDataMap()));
        }
        return ResultInfo.getFailInfo(ResultEnum.REMOTE_REQUEST_ERR.getCode(), responseBean.getResultMsg());
    }
}
