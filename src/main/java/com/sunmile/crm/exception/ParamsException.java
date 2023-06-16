package com.sunmile.crm.exception;

import lombok.Data;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/3 10:48
 * @Description
 * @Modified by Sunmile
 */
@Data
public class ParamsException extends RuntimeException{
    private Integer code = 300;
    private String msg = "出现参数异常";

    public ParamsException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public ParamsException(Integer code) {
        super("出现参数异常");
        this.code = code;
    }

    public ParamsException(String msg) {
        super(msg);
        this.code = 300;
        this.msg = msg;
    }
}
