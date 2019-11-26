package com.hongyusky.web.admin.controller;

import com.hongyusky.web.admin.annotation.UserLoginToken;
import com.hongyusky.web.admin.model.ResponseBean;
import com.hongyusky.web.admin.model.ResultInfo;
import com.hongyusky.web.admin.service.NewsService;
import com.hongyusky.web.admin.util.HttpUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 11:21 2019-10-21
 **/

@RestController
@RequestMapping(value = "/api")
@ResponseBody
public class WebController {
    @Autowired
    NewsService newsService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultInfo getNewsList(int pageNo, int pageSize) {
//        int pageIndex = StringUtils.isNotEmpty(currentPage) ? Integer.valueOf(currentPage) : 0;
//        int _pageSize = StringUtils.isNotEmpty(pageSize) ? Integer.valueOf(pageSize) : 10;
        return newsService.getNewsListForApi(pageNo, pageSize);
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public ResultInfo getDetail(@PathVariable("id") String id) {
        return newsService.getDetail(id);
    }

}
