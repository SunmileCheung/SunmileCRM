package com.sunmile.crm.dao;

import com.sunmile.crm.base.BaseMapper;
import com.sunmile.crm.query.CustomerOrderQuery;
import com.sunmile.crm.vo.CustomerOrder;

import java.util.List;

public interface CustomerOrderMapper extends BaseMapper<CustomerOrder,Integer> {
    //依据cus_id查询客户订单数据，提供给前端展示
    List<CustomerOrder> queryListForTableByCusId(CustomerOrderQuery customerOrderQuery);

    //最后下单时间

    /**
     * 查询id订单的最后下单时间
     * @param id 客户id
     * @return 返回对象
     */
    CustomerOrder queryLastCustomerOrderByCusId(Integer id);
}