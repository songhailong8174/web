package com.hongyusky.web.admin.util;

import org.apache.commons.lang.StringUtils;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 13:15 2019-10-06
 **/
public class StringUtil {

    public static final String REGEX_MOBILE = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9])|(16[6]))\\d{8}$";

    public static boolean isMobile(String mobile){
        if(StringUtils.isEmpty(mobile)){
            return false;
        }
        return mobile.matches(REGEX_MOBILE);
    }

    public static String getValidCode(){
        String random=(int)((Math.random()*9+1)*100000)+"";
        return random;
    }

    public static void main(String[] args) {
        boolean result = isMobile("17502515429");
        System.out.println(result);
    }
}
