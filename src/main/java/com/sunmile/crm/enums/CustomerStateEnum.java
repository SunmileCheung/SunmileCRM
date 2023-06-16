package com.sunmile.crm.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/9 17:20
 * @Description
 * @Modified by Sunmile
 */
@AllArgsConstructor
@NoArgsConstructor
public enum CustomerStateEnum {
    CREATION("fw_001","创建"),
    ASSIGN("fw_002","分配"),
    PROCE("fw_003","处理"),
    FEEDBACK("fw_004","反馈"),
    ARCHIVE("fw_005","归档");

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
