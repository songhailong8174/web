package com.hongyusky.web.admin.mapper;

import com.hongyusky.web.admin.model.City;
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
public interface CityMapper {


    /**
     * [查詢] 分頁查詢
     * @author songhailong
     * @date 2019/09/26
     **/
    List<City> pageList(@Param("pid") String pid);

}
