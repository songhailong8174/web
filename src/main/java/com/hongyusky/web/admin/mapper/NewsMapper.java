package com.hongyusky.web.admin.mapper;

import com.hongyusky.web.admin.model.News;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 00:13 2019-10-21
 **/
@Mapper
@Repository
public interface NewsMapper {
    /**
     * [新增]
     * @author songhailong
     * @date 2019/09/26
     **/
    int insert(News news);

    /**
     * [刪除]
     * @author songhailong
     * @date 2019/09/26
     **/
    int delete(@Param("id") String id);

    /**
     * [更新]
     * @author songhailong
     * @date 2019/09/26
     **/
    int update(News news);

    /**
     * [查詢] 根據主鍵 id 查詢
     * @author songhailong
     * @date 2019/09/26
     **/
    News load(@Param("id") String id);

    /**
     * [查詢] 根據主鍵 id 查詢
     * @author songhailong
     * @date 2019/09/26
     **/
    News loadNews(@Param("id") String id);


    /**
     * [查詢] 分頁查詢
     * @author songhailong
     * @date 2019/09/26
     **/
    List<News> pageList(@Param("offset") int offset,
                        @Param("pageSize") int pagesize);

    /**
     * [查詢] 分頁查詢 count
     * @author songhailong
     * @date 2019/09/26
     **/
    int pageListCount(@Param("offset") int offset,
                      @Param("pageSize") int pagesize);

    /**
     * [查詢] 分頁查詢
     * @author songhailong
     * @date 2019/09/26
     **/
    List<News> newsList(@Param("offset") int offset,
                        @Param("pageSize") int pagesize);

    /**
     * [查詢] 分頁查詢 count
     * @author songhailong
     * @date 2019/09/26
     **/
    int newsListCount(@Param("offset") int offset,
                      @Param("pageSize") int pagesize);
}
