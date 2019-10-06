package com.hongyusky.web.admin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 12:51 2019-09-24
 **/
@Component
@ConfigurationProperties(prefix = "ruihong")
public class SysConfig {
    /* 服务地址 */
    private String remote_url;
    /* api key */
    private String api_key;
    /* secret key */
    private String secret_key;

    public String getRemote_url() {
        return remote_url;
    }

    public void setRemote_url(String remote_url) {
        this.remote_url = remote_url;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getSecret_key() {
        return secret_key;
    }

    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }
}
