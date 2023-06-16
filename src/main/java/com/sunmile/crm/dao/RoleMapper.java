package com.sunmile.crm.dao;

import com.sunmile.crm.base.BaseMapper;
import com.sunmile.crm.vo.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleMapper extends BaseMapper<Role,Integer> {
    /**
     * 添加用户 和 编辑用户时使用
     * 返回指定用户的关联角色信息
     * @param userId 用户id
     * @return 返回查询到的用户分配角色信息
     */
    List<Map<String, Object>> queryAllRolesByUserId(Integer userId);

    /**
     * 依据角色名称查找相关的角色记录
     * @param roleName 角色名称
     * @return 返回查询到的角色信息
     */
    Role queryRoleInfoByName(String roleName);
}