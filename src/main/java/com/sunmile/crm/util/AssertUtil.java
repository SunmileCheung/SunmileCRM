package com.sunmile.crm.util;

import com.sunmile.crm.exception.ParamsException;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/3 10:51
 * @Description
 * @Modified by Sunmile
 */
public class AssertUtil {

    public static void isTrue(boolean flag,String msg){
        if (flag){
            throw new ParamsException(msg);
        }
    }
}
