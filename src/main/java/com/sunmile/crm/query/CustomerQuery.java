package com.sunmile.crm.query;

import com.sunmile.crm.base.BaseQuery;
import lombok.Data;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/8 10:43
 * @Description
 * @Modified by Sunmile
 */
@Data
public class CustomerQuery extends BaseQuery {
    //客户名
    private String cusName;
    //客户编号
    private String cusNo;
    //客户等级
    private String level;
    //金额区间
    private Integer type;

    private String time;

}
