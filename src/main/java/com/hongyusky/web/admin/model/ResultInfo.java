package com.hongyusky.web.admin.model;

import com.hongyusky.web.admin.status.ResultEnum;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 13:07 2019-09-24
 **/
public class ResultInfo implements java.io.Serializable {
    private Integer code;
    private String msg;
    private Object result;

    public ResultInfo(Integer code, String msg, Object result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }


    public static ResultInfo getSuccessInfo() {
        return new ResultInfo(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), null);
    }

    public static ResultInfo getSuccessInfo(Object result) {
        return new ResultInfo(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), result);
    }

    public static ResultInfo getFailInfo(ResultEnum resultEnum){
        return new ResultInfo(resultEnum.getCode(), resultEnum.getMsg(), null);
    }

    public static ResultInfo getFailInfo(){
        return new ResultInfo(ResultEnum.UNDEFINE.getCode(), ResultEnum.UNDEFINE.getMsg(), null);
    }

    public static ResultInfo getFailInfo(int code, String msg){
        return new ResultInfo(code, msg, null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
