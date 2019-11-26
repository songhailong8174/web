package com.hongyusky.web.admin.service;

import com.hongyusky.web.admin.mapper.NewsMapper;
import com.hongyusky.web.admin.model.News;
import com.hongyusky.web.admin.model.PageData;
import com.hongyusky.web.admin.model.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 00:42 2019-10-21
 **/
@Service
public class NewsService {
    @Autowired
    NewsMapper newsMapper;

    public void addNews (News news) {
        news.setCreateTime(new Date());
        news.setStatus(0);
        newsMapper.insert(news);
    }
    public void modifyNews (News news) {
        newsMapper.update(news);
    }

    public void publish(String id) {
        News news = newsMapper.load(id);
        news.setStatus(1);
        news.setPublishTime(new Date());
        newsMapper.update(news);
    }

    public void unPublish(String id) {
        News news = newsMapper.load(id);
        news.setStatus(0);
        news.setPublishTime(null);
        newsMapper.update(news);
    }

    public void delete(String id) {
        newsMapper.delete(id);
    }

    public ResultInfo getNewsList (int pageNo, int pageSize) {
        if(pageNo == 1) pageNo = 0;
        List<News> list = newsMapper.pageList(pageNo, pageSize);
        int rowCount = newsMapper.pageListCount(pageNo, pageSize);
        return ResultInfo.getSuccessInfo(PageData.buildResult(list, rowCount, pageNo, pageSize));
    }

    public ResultInfo getNewsListForApi(int pageNo, int pageSize) {
        if(pageNo == 1) pageNo = 0;
        List<News> list = newsMapper.newsList(pageNo, pageSize);
        int rowCount = newsMapper.newsListCount(pageNo, pageSize);
        return ResultInfo.getSuccessInfo(PageData.buildResult(list, rowCount, pageNo, pageSize));
    }

    public ResultInfo getDetail(String newsId) {
        return ResultInfo.getSuccessInfo(newsMapper.loadNews(newsId));
    }
}
