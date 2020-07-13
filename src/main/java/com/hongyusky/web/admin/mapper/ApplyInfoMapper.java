package com.hongyusky.web.admin.mapper;

import com.hongyusky.web.admin.model.ApplyInfo;
import com.hongyusky.web.admin.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ApplyInfoMapper {
    int insert(ApplyInfo applyInfo);
}
