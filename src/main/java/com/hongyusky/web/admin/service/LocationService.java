package com.hongyusky.web.admin.service;

import com.hongyusky.web.admin.mapper.CityMapper;
import com.hongyusky.web.admin.mapper.ProvinceMapper;
import com.hongyusky.web.admin.model.City;
import com.hongyusky.web.admin.model.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 21:54 2019-11-06
 **/
@Service
public class LocationService {

    @Autowired
    ProvinceMapper provinceMapper;
    @Autowired
    CityMapper cityMapper;

    public List<Province> queryProvinceList () {
        return provinceMapper.pageList();
    }

    public List<City> queryCityList (String pid) {
        return cityMapper.pageList(pid);
    }

}
