package com.sunmile.crm.dao;

import com.sunmile.crm.base.BaseMapper;
import com.sunmile.crm.vo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission,Integer> {

    public List<Integer> queryRoleHasAllModuleIdsByRoleId(Integer roleId);

    //依据roleId查询权限表中该roleId对应的权限记录数量
    Integer queryMidCountByRoleId(Integer roleId);

    //依据roleId对权限记录表中对应角色id的权限记录进行批量删除
    Integer deleteMidInfoByRoleId(Integer roleId);

    //通过userId从数据库中拿出权限信息，并写入session域中
    List<String> queryAllPermissionCodeByUserId(int userId);

    //通过传入的mid查找数据库中对应权限记录的条数
    Integer deletePermissionByMid(Integer mid);

    //先查询出其permission的数量
    Integer queryPermissonCountByMid(Integer mid);
}