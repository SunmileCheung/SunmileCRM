package com.sunmile.crm.enums;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/4 22:30
 * @Description 分配状态枚举类
 * @Modified by Sunmile
 */
public enum StateStatus {
    UNSTATED(0),
    STATED(1);

    private Integer type;

    StateStatus(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
