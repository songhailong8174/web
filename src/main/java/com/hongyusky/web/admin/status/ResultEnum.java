package com.hongyusky.web.admin.status;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 13:13 2019-09-24
 **/
public enum ResultEnum {

    SUCCESS(200, "操作成功"),
    LOGIN_USERNAME_ERR(301, "登录失败,用户名错误"),
    LOGIN_PASSWORD_ERR(302, "登录失败,密码错误"),
    USER_NOT_EXISTS(303, "用户不存在"),
    USER_MOBILE_EMPTY(304, "手机号为空"),
    USER_MOBILE_ERR(305, "手机号码格式错误"),
    USER_CODE_EMPTY(306, "验证码为空"),
    USER_CODE_ERR(307, "验证码错误"),
    USER_PASSWORD_EMPTY(308, "密码为空"),
    USER_EXISTS(309, "用户已存在"),
    USER_NAME_EMPTY(310, "用户名为空"),
    USER_NAME_ERR(311, "用户名格式错误"),
    USER_MOBILE_EXISTS(312, "该手机号已注册"),
    USER_REPASSWORD_EMPTY(313, "确认密码为空"),
    USER_PASSWORD_REPASSWORD_NOT_EQUALS(314, "两次输入的密码不一致"),


    REMOTE_REQUEST_ERR(-3, "调用远程方法错误"),
    PARAM_ERR(-2, "参数错误"),
    UNDEFINE(-1, "操作失败");


    private int code;
    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String msg(int code) {
        for (ResultEnum m : ResultEnum.values()) {
            if (m.getCode() == code) {
                return m.getMsg();
            }
        }
        return "";
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
