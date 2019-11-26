package com.hongyusky.web.admin.mapper;

import com.hongyusky.web.admin.model.News;
import com.hongyusky.web.admin.model.Province;
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
public interface ProvinceMapper {



    /**
     * [查詢] 分頁查詢
     * @author songhailong
     * @date 2019/09/26
     **/
    List<Province> pageList();
}
