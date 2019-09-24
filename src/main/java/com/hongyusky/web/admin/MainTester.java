package com.hongyusky.web.admin;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.hongyusky.web.admin.model.MessageBody;
import com.hongyusky.web.admin.model.Page;
import com.hongyusky.web.admin.model.ResponseBean;
import com.hongyusky.web.admin.util.OutsiteEncrptUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import com.cherry.utils.JacksonUtils;
import com.cherry.utils.http.sync.HttpConnectionManager;

public class MainTester {
	public static final Charset charset = Charset.forName("UTF-8");
	public static String requestUrl="http://122.96.149.114:8044/service/process";
	public static String userId="b4bd4e3bf6fc4d41af339c4a942a3a1a";
	public static void main(String[] args) {
//		System.out.println(UUID.randomUUID().toString().replace("-", ""));
		//accountQuery();
		registNotice();
		//usernameCheck();
		//sendSms();
		//smsDeliver();
		//queryHistorySms();
		//statSms();
		//statDaySms();
		//recharge();
		//faildReturn();
		//costDetail();
		//queryUserTemplates();
		//userTemplateApply();
		//delUserTemplate();
//		autoOpenAccount();
	}
	
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
	
	
	
	/**
	 * 注册信息同步，直客注册后，前端系统需要把注册信息的 名称、手机号同步到后台
	 */
	public static void registNotice(){
		Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
//		reqInfoDatas.put("userId",null);
		reqInfoDatas.put("mobile", "17502515429");
		reqInfoDatas.put("realName", "宋海龙");
		reqInfoDatas.put("company", "南京惠承通信息技术有限公司");
		ResponseBean sendHttp = sendHttp(10001, null, null, reqInfoDatas);
		System.out.println(sendHttp);
	}
	/**
	 * 注册信息审批完成后，同步信息到后台自动完成开户信息
	 */
	public static void autoOpenAccount(){
		String userId=UUID.randomUUID().toString().replace("-", "");
		Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
		reqInfoDatas.put("userId",userId);
		reqInfoDatas.put("mobile", "13382600756");
		reqInfoDatas.put("username", "test");
		reqInfoDatas.put("password", DigestUtils.md5Hex("test123"));
		reqInfoDatas.put("realName", "韩东");
		reqInfoDatas.put("smsPrice",0.01);
		reqInfoDatas.put("email","handong@rhwxsms.com");
		reqInfoDatas.put("moneyAccount","韩东");
		reqInfoDatas.put("accountNum","12334345345");
		reqInfoDatas.put("company", "测试信息技术有限公司");
		ResponseBean sendHttp = sendHttp(10002, null, null, reqInfoDatas);
		System.out.println(sendHttp);
	}

	/**
	 * 账户信息查询
	 */
	public static void accountQuery(){
		Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
		reqInfoDatas.put("userId",userId);
		ResponseBean sendHttp = sendHttp(10003, userId, null, reqInfoDatas);
		System.out.println(sendHttp);
	}
	
	/**
	 * 5.4用户名唯一性验证
	 */
	public static void usernameCheck(){
		Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
		reqInfoDatas.put("username","test");
		ResponseBean sendHttp = sendHttp(10004, userId, null, reqInfoDatas);
		System.out.println(sendHttp);
	}
	/**
	 * 提供修改账户密码的接口
	 */
	public static void updatePassword(){
		Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
		reqInfoDatas.put("oldPassword",DigestUtils.md5Hex("test"));
		reqInfoDatas.put("userId",userId);
		reqInfoDatas.put("newPassword",DigestUtils.md5Hex("456"));
		ResponseBean sendHttp = sendHttp(10005, userId, null, reqInfoDatas);
		System.out.println(sendHttp);
	}
	/**
	 * 修改账户信息
	 */
	public static void updateAccount(){
		Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
		reqInfoDatas.put("userId",userId);
		reqInfoDatas.put("password", DigestUtils.md5Hex("test"));
		reqInfoDatas.put("realName", "韩东");
		reqInfoDatas.put("smsPrice",0.01);
		reqInfoDatas.put("email","handong@rhwxsms.com");
		reqInfoDatas.put("moneyAccount","韩东");
		reqInfoDatas.put("accountNum","12334345345");
		reqInfoDatas.put("company", "南京惠承通信息技术有限公司");
		ResponseBean sendHttp = sendHttp(10006, userId, null, reqInfoDatas);
		System.out.println(sendHttp);
	}
	
	
	public static void sendSms(){
		Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
		reqInfoDatas.put("userId",userId);
		reqInfoDatas.put("mobiles","13382600756");
		reqInfoDatas.put("content", "【测试】123");
		reqInfoDatas.put("setTime", "2019-09-18 15:00:00");
		reqInfoDatas.put("srcNum", "123456");
		ResponseBean sendHttp = sendHttp(30001, userId, null, reqInfoDatas);
		System.out.println(sendHttp);
	}
	
	public static void smsDeliver(){
		Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
		reqInfoDatas.put("userId","cae5a079a94441d5ba5646ef601e21c2");
		Page	page = new Page(1,10);
		//reqInfoDatas.put("content", "【测试】123");
		reqInfoDatas.put("startTime", "2019-09-08 00:00:00");
		reqInfoDatas.put("endTime", "2019-09-18 16:00:00");
		ResponseBean sendHttp = sendHttp(50001, "cae5a079a94441d5ba5646ef601e21c2", page, reqInfoDatas);
		System.out.println(sendHttp);
	}
	

	public static void queryNerSms(){
		Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
		reqInfoDatas.put("userId","cae5a079a94441d5ba5646ef601e21c2");
		Page	page = new Page(1,10);
		//reqInfoDatas.put("content", "【测试】123");
		reqInfoDatas.put("startTime", "2019-09-18 00:00:00");
		reqInfoDatas.put("endTime", "2019-09-18 16:00:00");
		reqInfoDatas.put("mobiles", "18660908272,15261897347");
		ResponseBean sendHttp = sendHttp(50002, "cae5a079a94441d5ba5646ef601e21c2", page, reqInfoDatas);
		System.out.println(sendHttp);
	}
	public static void queryHistorySms(){
		Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
		reqInfoDatas.put("userId","cae5a079a94441d5ba5646ef601e21c2");
		Page	page = new Page(1,10);
		reqInfoDatas.put("content", "【医药城】");
		reqInfoDatas.put("startTime", "2019-09-12 00:00:00");
		reqInfoDatas.put("endTime", "2019-09-12 23:00:00");
		ResponseBean sendHttp = sendHttp(50003, "cae5a079a94441d5ba5646ef601e21c2", page, reqInfoDatas);
		System.out.println(sendHttp);
	}
	
	
	public static void statSms(){
		Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
		reqInfoDatas.put("userId","cae5a079a94441d5ba5646ef601e21c2");
		reqInfoDatas.put("startDay", "2019-09-01");
		reqInfoDatas.put("endDay", "2019-09-18");
		ResponseBean sendHttp = sendHttp(50004, "cae5a079a94441d5ba5646ef601e21c2", null, reqInfoDatas);
		System.out.println(sendHttp);
	}
	public static void statDaySms(){
		Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
		reqInfoDatas.put("userId","cae5a079a94441d5ba5646ef601e21c2");
		reqInfoDatas.put("startDay", "2019-09-01");
		reqInfoDatas.put("endDay", "2019-09-18");
		ResponseBean sendHttp = sendHttp(50005, "cae5a079a94441d5ba5646ef601e21c2", null, reqInfoDatas);
		System.out.println(sendHttp);
	}
	/**
	 * 充值记录
	 */
	public static void recharge(){
		Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
		reqInfoDatas.put("userId","cae5a079a94441d5ba5646ef601e21c2");
		reqInfoDatas.put("startDay", "2019-06-01");
		reqInfoDatas.put("endDay", "2019-09-18");
		ResponseBean sendHttp = sendHttp(40001, "cae5a079a94441d5ba5646ef601e21c2", null, reqInfoDatas);
		System.out.println(sendHttp);
	}
	
	/**
	 * 充值记录
	 */
	public static void faildReturn(){
		Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
		reqInfoDatas.put("userId","cae5a079a94441d5ba5646ef601e21c2");
		reqInfoDatas.put("startDay", "2019-06-01");
		reqInfoDatas.put("endDay", "2019-09-18");
		ResponseBean sendHttp = sendHttp(40002, "cae5a079a94441d5ba5646ef601e21c2", null, reqInfoDatas);
		System.out.println(sendHttp);
	}
	
	public static void costDetail(){
		Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
		reqInfoDatas.put("userId","cae5a079a94441d5ba5646ef601e21c2");
		reqInfoDatas.put("startDay", "2019-06-01");
		reqInfoDatas.put("endDay", "2019-09-18");
		ResponseBean sendHttp = sendHttp(40003, "cae5a079a94441d5ba5646ef601e21c2", null, reqInfoDatas);
		System.out.println(sendHttp);
	}
	
	/**
	 * 各人模板申请
	 */
	public static void userTemplateApply(){
		Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
		reqInfoDatas.put("userId",userId);
		reqInfoDatas.put("type", 1);
		reqInfoDatas.put("signature", "【韩东】");
		reqInfoDatas.put("title","验证码");
		reqInfoDatas.put("content","【韩东】您的验证码是123456，请妥善保管");
		ResponseBean sendHttp = sendHttp(20001, userId, null, reqInfoDatas);
		System.out.println(sendHttp);
	}
	
	/**
	 *个人模板
	 */
	public static void queryUserTemplates(){
		Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
		reqInfoDatas.put("userId","4650ccc3fd434ced9b7429840c1c462b");
		Page	page = new Page(1,10);
		reqInfoDatas.put("startDay", "2019-06-01");
		reqInfoDatas.put("endDay", "2019-09-18");
		ResponseBean sendHttp = sendHttp(20002, "4650ccc3fd434ced9b7429840c1c462b", page, reqInfoDatas);
		System.out.println(sendHttp);
	}
	
	public static void delUserTemplate(){
		Map<String,Object> reqInfoDatas = new HashMap<String, Object>();
		reqInfoDatas.put("userId","ef6e1c5996ca4bf2a1185456cdc61fad");
		reqInfoDatas.put("templateId","94c0a5f2b818499d8d7753f668d5e696");
		ResponseBean sendHttp = sendHttp(20003, "ef6e1c5996ca4bf2a1185456cdc61fad", null, reqInfoDatas);
		System.out.println(sendHttp);
		
	}
	
}
