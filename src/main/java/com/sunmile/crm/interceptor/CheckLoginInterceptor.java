package com.sunmile.crm.interceptor;

import com.sunmile.crm.dao.UserMapper;
import com.sunmile.crm.exception.NoLoginException;
import com.sunmile.crm.util.LoginUserUtil;
import com.sunmile.crm.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/4 10:38
 * @Description  登录拦截验证，如果是登录状态，cookie中有响应的用户信息，就放行
 * @Modified by Sunmile
 */
@Component
public class CheckLoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserMapper userMapper;

    /**
     * 对登录状态进行验证
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从数据库中拿出相应的userId
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        User user = userMapper.selectByPrimaryKey(userId);
        if (userId == 0 || Objects.isNull(user)){
            throw new NoLoginException("登录状态已更新，请重新登录");
        }
        return true;
    }


}
