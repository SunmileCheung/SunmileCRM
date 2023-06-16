package com.sunmile.crm.exception;

import lombok.Data;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/3 10:48
 * @Description
 * @Modified by Sunmile
 */
@Data
public class AuthPermissionException extends RuntimeException{
    private Integer code = 300;
    private String msg = "认证权限异常";

    public AuthPermissionException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public AuthPermissionException(Integer code) {
        super("认证权限异常");
        this.code = code;
    }

    public AuthPermissionException(String msg) {
        super(msg);
        this.code = 300;
        this.msg = msg;
    }
}
