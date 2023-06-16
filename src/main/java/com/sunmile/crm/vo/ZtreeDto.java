package com.sunmile.crm.vo;

import lombok.Data;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/6 22:25
 * @Description
 * @Modified by Sunmile
 */
@Data
public class ZtreeDto {
    private Integer id;
    private Integer pId;
    private String name;
    private boolean checked = false;
}
