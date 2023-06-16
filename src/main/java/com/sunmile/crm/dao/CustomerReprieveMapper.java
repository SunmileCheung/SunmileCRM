package com.sunmile.crm.dao;

import com.sunmile.crm.base.BaseMapper;
import com.sunmile.crm.vo.CustomerReprieve;

public interface CustomerReprieveMapper extends BaseMapper<CustomerReprieve,Integer> {


    //依据lossId查询CustomerReprieve记录
    CustomerReprieve queryCustomerReprieveById(Integer id);

    //通过id检索数据库中有效的tomerReprieve记录
    CustomerReprieve selectByPrimaryKeyWithIsValid(Integer id);
}