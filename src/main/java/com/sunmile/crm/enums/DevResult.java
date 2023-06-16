package com.sunmile.crm.enums;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/4 22:33
 * @Description 开发状态枚举类
 * @Modified by Sunmile
 */
public enum DevResult {
    //未开发
    UNDEV(0),
    //开发中
    DEVING(1),
    //开发成功
    DEV_SUCCESS(2),
    //开发失败
    DEV_FAILED(3);

    private Integer status;

    DevResult(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
