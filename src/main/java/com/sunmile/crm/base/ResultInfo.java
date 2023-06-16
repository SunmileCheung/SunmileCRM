package com.sunmile.crm.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultInfo<T> {
    private Integer code=300;
    private String msg="failed";

    private T result;

    /**
     * 成功操作后返回的信息和数据 code值默认为200
     * @param msg 返回信息
     * @param result 返回数据
     */
    public void success(String msg,T result){
        this.code = 200;
        this.msg = msg;
        this.result = result;
    }

    /**
     * 操作失败后返回的信息和数据 code值默认为500
     * @param msg 信息
     * @param result 数据
     */
    public void  failed(String msg,T result){
        this.code = 500;
        this.msg = msg;
        this.result = result;
    }
}
