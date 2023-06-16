package com.sunmile.crm.query;

import com.sunmile.crm.base.BaseQuery;
import lombok.Data;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/4 11:52
 * @Description
 * @Modified by Sunmile
 */
@Data
public class SaleChanceQuery extends BaseQuery {

    //客户名
    private String customerName;
    //创建人
    private String createMan;
    //分配状态
    private Integer state;

    //开发状态
    private Integer devResult;
    //分配人
    private Integer assignMan;

}
