package com.hongyusky.web.admin.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 10:35 2019-10-08
 **/
public class PageData implements java.io.Serializable {
    private Object list;
    private Pagination pagination;

    public PageData(){}
    public PageData(Object list, Pagination pagination){
        this.list = list;
        this.pagination = pagination;
    }

    public static PageData buildResult(Map<String, Object> dataMap){
        if(dataMap == null) return new PageData();
        Object list = dataMap.get("list");
        System.out.println(dataMap.get("page") instanceof LinkedHashMap);
        LinkedHashMap map = (LinkedHashMap)dataMap.get("page");
        System.out.println(map);
        Pagination pagination = new Pagination(
                Integer.valueOf(map.get("pageIndex").toString()),
                Integer.valueOf(map.get("pageSize").toString()),
                Integer.valueOf(map.get("rowCount").toString()));
        return new PageData(list, pagination);
    }

    public static PageData buildResult(Object list, int rowCount, int pageIndex, int pageSize){
        Pagination pagination = new Pagination(
                pageIndex,
                pageSize,
                rowCount);
        return new PageData(list, pagination);
    }

    public Object getList() {
        return list;
    }

    public void setList(Object list) {
        this.list = list;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
