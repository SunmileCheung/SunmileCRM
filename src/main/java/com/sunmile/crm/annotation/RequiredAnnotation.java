package com.sunmile.crm.annotation;

import javax.annotation.Resource;
import java.lang.annotation.*;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/7 11:20
 * @Description
 * @Modified by Sunmile
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface RequiredAnnotation {
    String code() default "";
}
