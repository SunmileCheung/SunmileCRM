package com.sunmile.crm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/3 10:57
 * @Description
 * @Modified by Sunmile
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserModel {
    //private Integer userId;
    private String userIdStr;
    private String userName;
    private String trueName;
}
