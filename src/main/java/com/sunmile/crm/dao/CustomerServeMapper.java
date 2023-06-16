package com.sunmile.crm.dao;

import com.sunmile.crm.base.BaseMapper;
import com.sunmile.crm.query.CustomerServeQuery;
import com.sunmile.crm.vo.CustomerServe;

import java.util.List;
import java.util.Map;

public interface CustomerServeMapper extends BaseMapper<CustomerServe,Integer> {

    //查找数据库中的有效CustomerServe数据
    CustomerServe selectByPrimaryKeyWithValid(Integer id);

    List<Map<String, Object>> queryCustomerServeInfo(CustomerServeQuery customerServeQuery);

    //以用户名为维度统计服务数量
    List<Map<String, Object>> customerServeInfo();


}