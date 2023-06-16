package com.sunmile.crm.query;

import com.sunmile.crm.base.BaseQuery;
import lombok.Data;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/6 8:53
 * @Description
 * @Modified by Sunmile
 */
@Data
public class UserQuery extends BaseQuery {

    private String userName;
    private String email;
    private String phone;
}
