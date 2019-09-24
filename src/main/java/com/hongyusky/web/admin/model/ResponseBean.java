package com.hongyusky.web.admin.model;

import java.util.Map;

public class ResponseBean{
	/**
	 * 消息id，唯一
	 */
	private String messageId;
	
	private int resultCode=200;
	private String resultMsg;
	//加密的数据，还原解析后应该是Map<String,Object>
	private String data;
	
	private Map<String,Object> dataMap;
	
	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public String getData() {
		return data;
	}

	
	public void setData(String data) {
		this.data = data;
	}

	
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
	
	public boolean isSuccess(){
		return resultCode==200;
	}

	@Override
	public String toString() {
		StringBuilder str=new StringBuilder();
		str.append("ResponseBean[messageId=").append(messageId).append(",resultCode:").append(resultCode).append(",resultMsg:").append(resultMsg);
		str.append(",dataMap:").append(dataMap);
		return str.toString();
	}
}
