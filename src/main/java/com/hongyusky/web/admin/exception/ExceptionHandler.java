package com.hongyusky.web.admin.exception;

import com.hongyusky.web.admin.model.ResultInfo;
import com.hongyusky.web.admin.status.ResultEnum;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: songhailong
 * @Description:
 * @Date Created in 00:28 2019-10-09
 **/

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(SysException.class)
    @ResponseBody
    public ResultInfo handleStudentException(HttpServletRequest request, SysException ex) {
        return ResultInfo.getFailInfo(ex.getResponse());
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultInfo handleException(HttpServletRequest request, Exception ex) {
//        log.error("exception error:{}",ex);
        return ResultInfo.getFailInfo(ResultEnum.UNDEFINE.getCode(), ex.getMessage());
    }

}
