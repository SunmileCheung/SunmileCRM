package com.sunmile.crm.dao;

import com.sunmile.crm.base.BaseMapper;
import com.sunmile.crm.vo.CustomerLinkMan;

public interface CustomerLinkManMapper extends BaseMapper<CustomerLinkMan,Integer> {
    //通过cid查询联系人信息
    CustomerLinkMan selectByCustomerId(Integer cid);

    //通过linkName查询数据库中是否存在同名的联系人信息
    CustomerLinkMan queryCustomerLinkManByLinkName(String linkName);
}