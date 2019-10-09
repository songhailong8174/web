package com.hongyusky.web.admin.exception;

import com.hongyusky.web.admin.status.ResultEnum;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 00:23 2019-10-09
 **/
public class SysException extends RuntimeException {

    private final ResultEnum response;

    public ResultEnum getResponse() {
        return response;
    }

    public SysException (ResultEnum response) {
        this.response = response;
    }


}
