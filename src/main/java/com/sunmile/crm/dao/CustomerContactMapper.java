package com.sunmile.crm.dao;

import com.sunmile.crm.base.BaseMapper;
import com.sunmile.crm.vo.CustomerContact;

public interface CustomerContactMapper extends BaseMapper<CustomerContact,Integer> {

    //依据cid查询来往记录
    CustomerContact selectCustomerContactByCid(Integer cid);
}