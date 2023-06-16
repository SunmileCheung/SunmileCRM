package com.sunmile.crm.query;

import com.sunmile.crm.base.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/9 11:02
 * @Description
 * @Modified by Sunmile
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerReprieveQuery extends BaseQuery {

    private Integer lossId;
}
