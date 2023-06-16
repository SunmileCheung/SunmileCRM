package com.sunmile.crm.query;

import com.sunmile.crm.base.BaseQuery;
import lombok.Data;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/9 16:21
 * @Description
 * @Modified by Sunmile
 */

@Data
public class CustomerServeQuery extends BaseQuery {

    //客户名
    private String customer;
    //服务类型
    private String serveType;
    //服务进度
    private String state;
    //服务分配人
    private Integer assigner;
    //服务处理人
    private String serviceProcePeople;
}
