package com.hongyusky.web.admin.util;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 13:15 2019-10-06
 **/
public class StringUtil {

    public static final String REGEX_MOBILE = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9])|(16[6]))\\d{8}$";

    /**
     * @param checkType 校验类型：0校验手机号码，1校验座机号码，2两者都校验满足其一就可
     * @param phoneNum
     * */
    public static boolean validPhoneNum(String checkType,String phoneNum){
        boolean flag=false;
        Pattern p1 = null;
        Pattern p2 = null;
        Matcher m = null;
        p1 = Pattern.compile(REGEX_MOBILE);
        p2 = Pattern.compile("^(0[0-9]{2,3}\\-)?([1-9][0-9]{6,7})$");
        if("0".equals(checkType)){
            System.out.println(phoneNum.length());
            if(phoneNum.length() !=11){
                return false;
            }else{
                m = p1.matcher(phoneNum);
                flag = m.matches();
            }
        }else if("1".equals(checkType)){
            if(phoneNum.length()<11||phoneNum.length()>=16){
                return false;
            }else{
                m = p2.matcher(phoneNum);
                flag = m.matches();
            }
        }else if("2".equals(checkType)){
            if(!((phoneNum.length() == 11 && p1.matcher(phoneNum).matches())||(phoneNum.length()<16&&p2.matcher(phoneNum).matches()))){
                return false;
            }else{
                flag = true;
            }
        }
        return flag;
    }

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
