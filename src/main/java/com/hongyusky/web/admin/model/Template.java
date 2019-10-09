package com.hongyusky.web.admin.model;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 12:42 2019-10-08
 **/
public class Template implements java.io.Serializable {
    /**
     * 类型 1 验证码类 2 通知类
     */
    private int type;

    /**
     * 短信签名
     */
    private String signature;

    private String title;

    private String content;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
