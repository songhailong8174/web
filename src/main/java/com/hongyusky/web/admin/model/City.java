package com.hongyusky.web.admin.model;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 21:38 2019-11-06
 **/
public class City implements java.io.Serializable {
    private String id;
    private String name;
    private String province;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
