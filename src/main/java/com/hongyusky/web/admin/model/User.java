package com.hongyusky.web.admin.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 10:43 2019-09-26
 **/
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * userid
     */
    private String userid;

    /**
     * username
     */
    private String username;

    /**
     * mobile
     */
    private String mobile;

    /**
     * password
     */
    private String password;

    /**
     * 企业联系人
     */
    private String realname;

    /**
     * 短信单价
     */
    private BigDecimal smsprice;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 对公或对私账号名称
     */
    private String moneyaccount;

    /**
     * 对公或对私账号
     */
    private String accountnum;

    /**
     * 企业名称
     */
    private String company;

    /**
     * 10正常 6冻结
     */
    private Integer status;

    /**
     * 认证状态 0 未认证 1 已认证
     */
    private Integer authstatus;

    /**
     * 纳税人识别号
     */
    private String taxno;

    /**
     * 省
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 地址
     */
    private String address;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 微信号
     */
    private String wx;

    /**
     * 营业执照
     */
    private String license;

    /**
     * 创建时间
     */
    private Date createdate;

    /**
     * 修改时间
     */
    private Date modifydate;

    /**
     * 角色 0 普通用户 1 管理员
     */
    private int role;

    private String provicename;

    private String cityname;


    public User() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public BigDecimal getSmsprice() {
        return smsprice;
    }

    public void setSmsprice(BigDecimal smsprice) {
        this.smsprice = smsprice;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMoneyaccount() {
        return moneyaccount;
    }

    public void setMoneyaccount(String moneyaccount) {
        this.moneyaccount = moneyaccount;
    }

    public String getAccountnum() {
        return accountnum;
    }

    public void setAccountnum(String accountnum) {
        this.accountnum = accountnum;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAuthstatus() {
        return authstatus;
    }

    public void setAuthstatus(Integer authstatus) {
        this.authstatus = authstatus;
    }

    public String getTaxno() {
        return taxno;
    }

    public void setTaxno(String taxno) {
        this.taxno = taxno;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getProvicename() {
        return provicename;
    }

    public void setProvicename(String provicename) {
        this.provicename = provicename;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }
}
