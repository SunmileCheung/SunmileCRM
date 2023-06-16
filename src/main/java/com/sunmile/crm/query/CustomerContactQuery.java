package com.sunmile.crm.query;

import com.sunmile.crm.base.BaseQuery;
import lombok.Data;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/11 16:36
 * @Description
 * @Modified by Sunmile
 */
@Data
public class CustomerContactQuery extends BaseQuery {

    private Integer cid;
    private String cusName;
    private String contactTime;

}
