package com.sunmile.crm.aop;

import com.sunmile.crm.annotation.RequiredAnnotation;
import com.sunmile.crm.exception.AuthPermissionException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/7 11:23
 * @Description
 * @Modified by Sunmile
 */
@Component
@Aspect
public class PermissionProxy {

    @Autowired
    private HttpSession session;

    @Around(value = "@annotation(com.sunmile.crm.annotation.RequiredAnnotation)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object obj = null;

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            RequiredAnnotation requiredAnnotation = method.getDeclaredAnnotation(RequiredAnnotation.class);
            String code = requiredAnnotation.code();
            List<String> permissions = (List<String>) session.getAttribute("permissions");
            if (null == permissions || permissions.size() < 1) throw new AuthPermissionException("认证权限异常");
            if (permissions.contains(code)){
                obj = joinPoint.proceed();
            }else {
                throw new AuthPermissionException("认证权限异常");
            }
        return obj;
    }
}
