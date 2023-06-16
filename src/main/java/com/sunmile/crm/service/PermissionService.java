package com.sunmile.crm.service;

import com.sunmile.crm.base.BaseService;
import com.sunmile.crm.dao.PermissionMapper;
import com.sunmile.crm.vo.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/6 22:18
 * @Description
 * @Modified by Sunmile
 */
@Service
public class PermissionService extends BaseService<Permission,Integer> {
    @Resource
    private PermissionMapper permissionMapper;

    /**
     *  //从权限表中查出所有的已经赋予了角色权限的权限id
     * @param roleId
     * @return
     */
    public List<Integer> queryRoleHasAllModuleIdsByRoleId(Integer roleId){
        return permissionMapper.queryRoleHasAllModuleIdsByRoleId(roleId);
    }

    //通过userId从数据库中拿出权限信息，并写入session域中
    public List<String> queryAllPermissionCodeByUserId(int userId) {
        return permissionMapper.queryAllPermissionCodeByUserId(userId);
    }

    //通过传入的mid查找数据库中对应权限记录的条数
    public Integer deletePermissionByMid(Integer mid) {
       return permissionMapper.deletePermissionByMid(mid);

    }

    //要删除module记录还需要删除权限表的记录 先查询出其permission的数量
    public Integer queryPermissonCountByMid(Integer mid) {
        return permissionMapper.queryPermissonCountByMid(mid);
    }
}
