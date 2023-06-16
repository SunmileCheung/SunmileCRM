package com.sunmile.crm.dao;

import com.sunmile.crm.base.BaseMapper;
import com.sunmile.crm.vo.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole,Integer> {

    //首先查询该用户下是否存在角色数据
    Integer queryRoleCountByUserId(Integer userId);
    //如果数据库中存在角色信息，首先进行清除操作
    Integer deleteUserRoleByUserId(Integer userId);
}