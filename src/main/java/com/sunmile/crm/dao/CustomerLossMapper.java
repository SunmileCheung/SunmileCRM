package com.sunmile.crm.dao;

import com.sunmile.crm.base.BaseMapper;
import com.sunmile.crm.vo.CustomerLoss;

public interface CustomerLossMapper extends BaseMapper<CustomerLoss,Integer> {

    //从数据库中检索该id对应的记录
    CustomerLoss selectByPrimaryKeyWithIsValid(Integer id);
}