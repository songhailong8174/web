package com.hongyusky.web.admin.model;

import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 @author cherry
 @version 0.1
 @desc:请求的内容信息
 */
public class MessageBody {

	private String userId;
	/**
	 * 超时时间，可支持自定义
	 */
	private int timeout=30000;
	private int serviceType;
	private Page page;
	private Map<String,Object> reqInfoDatas;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public int getServiceType() {
		return serviceType;
	}
	public void setServiceType(int serviceType) {
		this.serviceType = serviceType;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public Map<String, Object> getReqInfoDatas() {
		return reqInfoDatas;
	}
	public void setReqInfoDatas(Map<String, Object> reqInfoDatas) {
		this.reqInfoDatas = reqInfoDatas;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
