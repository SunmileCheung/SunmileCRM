package com.sunmile.crm.exception;

import com.sunmile.crm.base.ResultInfo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/3 11:26
 * @Description
 * @Modified by Sunmile
 */
//@ControllerAdvice
public class GlobalExceptionResolver {

    @ExceptionHandler(ParamsException.class)
    @ResponseBody
    public ResultInfo exceptionResolver(ParamsException ex){
        ResultInfo<Object> resultInfo = new ResultInfo<>();
        resultInfo.failed(ex.getMsg(),null);
        return resultInfo;
    }
    @ExceptionHandler
    @ResponseBody
    public ResultInfo exceptionResolver(Exception ex){
        ResultInfo<Object> resultInfo = new ResultInfo<>();
        resultInfo.failed(ex.getMessage(),null);
        return resultInfo;
    }
}
