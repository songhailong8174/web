package com.hongyusky.web.admin.mapper;

import com.hongyusky.web.admin.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 09:10 2019-09-26
 **/
@Mapper
@Repository
public interface UserMapper {
    /**
     * [新增]
     * @author songhailong
     * @date 2019/09/26
     **/
    int insert(User user);

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
    int update(User user);

    /**
     * [查詢] 根據主鍵 id 查詢
     * @author songhailong
     * @date 2019/09/26
     **/
    User load(@Param("userid") String userid);

    User loadByUserName(@Param("userName") String userName);

    User loadByMobile(@Param("mobile") String mobile);

    /**
     * [查詢] 分頁查詢
     * @author songhailong
     * @date 2019/09/26
     **/
    List<User> pageList(@Param("offset") int offset,
                        @Param("pagesize") int pagesize);

    /**
     * [查詢] 分頁查詢 count
     * @author songhailong
     * @date 2019/09/26
     **/
    int pageListCount(@Param("offset") int offset,
                      @Param("pagesize") int pagesize);
}
