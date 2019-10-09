package com.hongyusky.web.admin.model;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 10:30 2019-10-08
 **/
public class Pagination implements java.io.Serializable {
    private int current;
    private int pageSize;
    private int total;

    public Pagination(){}
    public Pagination(int current, int pageSize, int total){
        this.current = current;
        this.pageSize = pageSize;
        this.total = total;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
