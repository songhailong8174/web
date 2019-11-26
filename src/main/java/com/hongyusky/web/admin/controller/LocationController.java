package com.hongyusky.web.admin.controller;

import com.hongyusky.web.admin.model.City;
import com.hongyusky.web.admin.model.Province;
import com.hongyusky.web.admin.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 21:57 2019-11-06
 **/
@RestController
@RequestMapping(value = "/geographic")
@ResponseBody
public class LocationController {

    @Autowired
    LocationService locationService;

    @RequestMapping(value = "/province", method = RequestMethod.GET)
    public List<Province> queryPro() {
        return locationService.queryProvinceList();
    }

    @RequestMapping(value = "/city/{id}", method = RequestMethod.GET)
    public List<City> queryCity(@PathVariable("id") String id) {
        return locationService.queryCityList(id);
    }
}
