package com.sunmile.crm.query;

import com.sunmile.crm.base.BaseQuery;
import lombok.Data;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/9 10:31
 * @Description
 * @Modified by Sunmile
 */

@Data
public class CustomerLossQuery extends BaseQuery {

    private String cusNo;
    private String cusName;
    private Integer state;
}
