package com.hongyusky.web.admin.util;

import com.cherry.utils.JacksonUtils;
import com.cherry.utils.http.sync.HttpConnectionManager;
import com.hongyusky.web.admin.model.MessageBody;
import com.hongyusky.web.admin.model.Page;
import com.hongyusky.web.admin.model.ResponseBean;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.nio.charset.Charset;
import java.util.Map;

@Component
public class HttpUtils {

    public static final Charset charset = Charset.forName("UTF-8");
    public static String requestUrl="http://221.130.30.188:8022/service/process";
    public static String userId="b4bd4e3bf6fc4d41af339c4a942a3a1a";

    /***
     * 发送http请求
     * @param serviceType
     * @param userId
     * @param page
     * @param reqInfoDatas
     * @return
     */
    public static ResponseBean sendHttp(int serviceType, String userId, Page page, Map<String,Object> reqInfoDatas){
        ResponseBean bean = new ResponseBean();
        String result = "";
        //主体参数
        MessageBody body = new MessageBody();
        body.setUserId(userId);
        body.setPage(page);
        body.setServiceType(serviceType);
        body.setReqInfoDatas(reqInfoDatas);
        try{
            String headerAuthorization = OutsiteEncrptUtils.genderHeaderAuthorization();
            String messageBody = OutsiteEncrptUtils.encryptReqInfo(JacksonUtils.getInstance().writerJavaObject2JSON(body));
            HttpPost httpPost = HttpConnectionManager.buildHttpRequest(requestUrl, null);
            httpPost.addHeader("Authorization", headerAuthorization);
            httpPost.addHeader("Content-Type", "application/json");
            StringEntity entiy = new StringEntity(messageBody,charset);
            httpPost.setEntity(entiy);
            result = HttpConnectionManager.getStringResult(httpPost);
            if(StringUtils.isNotEmpty(result)){
                bean = JacksonUtils.getInstance().readJSON2Bean(ResponseBean.class, result);
                if(bean!=null && bean.isSuccess()){
                    String data = bean.getData();
                    if(StringUtils.isNotEmpty(data)){
                        String decryptMsg = OutsiteEncrptUtils.decryptMsg(data);
                        if(StringUtils.isNotEmpty(decryptMsg)){
                            bean.setDataMap(JacksonUtils.getInstance().readJSON2Map(decryptMsg));
                        }
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("result:"+result+",exception:"+e.getMessage());
            bean.setResultCode(400);
            bean.setResultMsg(e.getMessage());
        }
        return bean;
    }
}
