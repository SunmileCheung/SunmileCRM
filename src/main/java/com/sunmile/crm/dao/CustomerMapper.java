package com.sunmile.crm.dao;

import com.sunmile.crm.base.BaseMapper;
import com.sunmile.crm.query.CustomerQuery;
import com.sunmile.crm.vo.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerMapper extends BaseMapper<Customer,Integer> {

    //通过客户名称查询客户信息
    Customer queryCustomerInfoByCustomerName(String name);

    List<Map<String, Object>> queryOrderDetailPageByOrderId(Integer orderId);

    //查询流失客户
    List<Customer> queryLossCustomers();


    //依据id组成的集合进行customer状态的更新操作
    Integer updateCustomerStateByIds(List<Integer> lossCusIds);

    //查询客户贡献分析报表
    List<Map<String, Object>> queryCustomerContributionByParams(CustomerQuery customerQuery);



    /**
     *   //查询客户构成信息
     * @return 返回map  key为 level； value为level对应的数量
     */
    List<Map<String, Object>> countCustoerMake();

}