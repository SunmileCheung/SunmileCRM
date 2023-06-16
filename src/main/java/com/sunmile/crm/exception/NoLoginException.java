package com.sunmile.crm.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/4 10:32
 * @Description
 * @Modified by Sunmile
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoLoginException extends RuntimeException{

    private Integer code = 501;
    private String msg = "出现登录问题";

    public NoLoginException(Integer code) {
        super("出现登录问题");
        this.code = code;
    }

    public NoLoginException(String msg) {
        super(msg);
        this.msg = msg;
    }

}
