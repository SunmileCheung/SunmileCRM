package com.sunmile.crm.dao;

import com.sunmile.crm.base.BaseMapper;
import com.sunmile.crm.vo.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper extends BaseMapper<User,Integer> {

    User queryUserInfoByUserName(String userName);

    //查询所有的销售人员信息
    List<Map<String, Object>> queryAllSales();

    //查询所有的客户经理
    List<Map<String, Object>> queryAllCustomerManager();


}