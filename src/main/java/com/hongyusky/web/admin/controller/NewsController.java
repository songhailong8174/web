package com.hongyusky.web.admin.controller;

import com.hongyusky.web.admin.annotation.UserLoginToken;
import com.hongyusky.web.admin.model.News;
import com.hongyusky.web.admin.model.ResultInfo;
import com.hongyusky.web.admin.service.NewsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 08:21 2019-10-21
 **/
@RestController
@RequestMapping(value = "/news")
@ResponseBody
public class NewsController {

    @Autowired
    NewsService newsService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @UserLoginToken
    public ResultInfo addNews(News news) {
        System.out.println(news);
        newsService.addNews(news);
        return ResultInfo.getSuccessInfo();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @UserLoginToken
    public ResultInfo getNewsList(String currentPage, String pageSize) {
        int pageIndex = StringUtils.isNotEmpty(currentPage) ? Integer.valueOf(currentPage) : 0;
        int _pageSize = StringUtils.isNotEmpty(pageSize) ? Integer.valueOf(pageSize) : 10;
        return newsService.getNewsList(pageIndex, _pageSize);
    }

    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    @UserLoginToken
    public ResultInfo publish(String id) {
        newsService.publish(id);
        return ResultInfo.getSuccessInfo();
    }

    @RequestMapping(value = "/unPublish", method = RequestMethod.POST)
    @UserLoginToken
    public ResultInfo unPublish(String id) {
        newsService.unPublish(id);
        return ResultInfo.getSuccessInfo();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @UserLoginToken
    public ResultInfo delete(String id) {
        newsService.delete(id);
        return ResultInfo.getSuccessInfo();
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @UserLoginToken
    public ResultInfo modify(News news) {
       newsService.modifyNews(news);
        return ResultInfo.getSuccessInfo();
    }

}
