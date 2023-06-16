package com.sunmile.crm.exception;

import com.sunmile.crm.base.ResultInfo;
import com.sunmile.crm.util.JSONUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/3 17:04
 * @Description 异常统一处理类
 * @Modified by Sunmile
 */
@Component
public class GlobalExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        //首先设置默认显示
        ModelAndView modelAndView = new ModelAndView("error");
        //判断是否出现登录异常，如果出现登录异常，重定向到登陆页面
        if (e instanceof NoLoginException){
            modelAndView.setViewName("redirect:index");
            return modelAndView;
        }
        modelAndView.addObject("code",500);
        modelAndView.addObject("msg","出现异常");
        //判断如果是 HandlerMethod类型 进行操作
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //获取方法上是否有responsebody注解
            ResponseBody responseBody = handlerMethod.getMethodAnnotation(ResponseBody.class);
            //获取类上是否有restController注解
            RestController restController = handlerMethod.getClass().getDeclaredAnnotation(RestController.class);
            //如果该方法上存在response注解，就返回json格式的数据
            if (Objects.nonNull(responseBody) || Objects.nonNull(restController)){
                //新建一个ResultInfo对象，其默认为失败返回
                ResultInfo<Object> rs = new ResultInfo<>(500,"系统错误，请稍后重试",null);
                //判断如果是ParamsException异常类，将code和msg数据写入
                if (e instanceof ParamsException){
                    ParamsException ex = (ParamsException) e;
                    rs.failed(ex.getMsg(),null);
                    rs.setCode(ex.getCode());
                } else if (e instanceof AuthPermissionException) {
                    AuthPermissionException ex = (AuthPermissionException) e;
                    rs.failed(ex.getMsg(),null);
                    rs.setCode(ex.getCode());
                }
                JSONUtil.toJson(response,rs);
                return null;
            }else {
                if (e instanceof ParamsException){
                    ParamsException ex = (ParamsException) e;
                    modelAndView.addObject("msg",ex.getMsg());
                    modelAndView.addObject("code",ex.getCode());
                } else if (e instanceof AuthPermissionException) {
                    AuthPermissionException ex = (AuthPermissionException) e;
                    modelAndView.addObject("msg",ex.getMsg());
                    modelAndView.addObject("code",ex.getCode());
                }
                return modelAndView;
            }
        }else {
            //如果方法类型不是methodHandle，返回默认视图
            return modelAndView;
        }
    }
}
