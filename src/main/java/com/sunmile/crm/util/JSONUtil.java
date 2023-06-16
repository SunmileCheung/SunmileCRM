package com.sunmile.crm.util;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/3 17:13
 * @Description
 * @Modified by Sunmile
 */
public class JSONUtil {

    /**
     * 通过response写出一个json字符串给前端
     * @param response 响应对象
     * @param obj 要被写出的Object类
     */
    public static void toJson(HttpServletResponse response,Object obj){
        PrintWriter writer = null;
        try {
            response.setContentType("application/json;charset=utf-8");
            writer = response.getWriter();
            String jsonStr = JSONObject.toJSONString(obj);
            writer.write(jsonStr);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }finally {
            if (Objects.nonNull(writer)){
                writer.close();
            }
        }
    }
}
