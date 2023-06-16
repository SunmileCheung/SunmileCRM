package com.sunmile.crm.query;

import com.sunmile.crm.base.BaseQuery;
import lombok.Data;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/8 16:57
 * @Description
 * @Modified by Sunmile
 */
@Data
public class OrderDetailQuery extends BaseQuery {
    private Integer orderId;
}
