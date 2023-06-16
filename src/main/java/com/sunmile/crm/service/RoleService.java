package com.sunmile.crm.service;

import com.sunmile.crm.base.BaseService;
import com.sunmile.crm.dao.PermissionMapper;
import com.sunmile.crm.dao.RoleMapper;
import com.sunmile.crm.util.AssertUtil;
import com.sunmile.crm.vo.Permission;
import com.sunmile.crm.vo.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/6 11:46
 * @Description
 * @Modified by Sunmile
 */
@Service
public class RoleService extends BaseService<Role,Integer> {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private ModuleService moduleService;

    /**
     * 添加用户 和 编辑用户时使用
     * 返回指定用户的关联角色信息
     * @param userId 用户id
     * @return 返回查询到的用户分配角色信息
     */
    public List<Map<String, Object>> queryAllRoles(Integer userId) {
        //AssertUtil.isTrue(Objects.isNull(userId),"用户ID不能为空");
        return roleMapper.queryAllRolesByUserId(userId);

    }
    /**
     * 对角色信息进行新增操作
     * @param role 角色 需要写入数据库中的角色信息
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveRole(Role role) {
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"角色名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleRemark()),"角色备注不能为空");
        //依据角色名称查找相关的角色记录
        Role temp = roleMapper.queryRoleInfoByName(role.getRoleName());
        AssertUtil.isTrue(Objects.nonNull(temp),"该角色信息已存在");
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        role.setIsValid(1);
        AssertUtil.isTrue(roleMapper.insertSelective(role) != 1,"角色信息添加失败");
    }

    /**
     * 对角色信息进行修改操作
     * @param role 角色 需要进行修改的数据库角色信息
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateRole(Role role) {
        AssertUtil.isTrue(Objects.isNull(role.getId()),"角色id不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"角色名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleRemark()),"角色备注不能为空");
        //依据角色名称查找相关的角色记录
        Role temp = roleMapper.queryRoleInfoByName(role.getRoleName());
        //判断数据库中是否已存在同名的角色信息
        AssertUtil.isTrue(!temp.getId().equals(role.getId()),"想要修改的角色名称已存在");
        //更新修改时间
        role.setUpdateDate(new Date());
        role.setIsValid(1);
        AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role) != 1,"角色信息更新失败");
    }

    /**
     * 删除角色信息
     * @param roleId 角色id
     * @return 返回删除结果
     */
    public void deleteRoleByRoleId(Integer roleId) {
        AssertUtil.isTrue(Objects.isNull(roleId),"角色id不能为空");
        Role role = roleMapper.selectByPrimaryKey(roleId);
        AssertUtil.isTrue(Objects.isNull(role),"该角色id对应的角色信息为空");
        role.setIsValid(0);
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role) < 1,"角色信息删除失败");
    }

    /**
     * 根据前端传过来的角色id和moduleId进行权限的更改
     * @param mids moduleId
     * @param roleId 角色id
     * @return
     */
    public void addGrantByMidsAndRoleId(Integer[] mids, Integer roleId) {
        AssertUtil.isTrue(Objects.isNull(roleId),"角色id不能为空");
        Integer count = permissionMapper.queryMidCountByRoleId(roleId);
        if (count > 0){
            Integer deleteCount = permissionMapper.deleteMidInfoByRoleId(roleId);
            AssertUtil.isTrue(deleteCount != count,"前置删除操作失败");
        }
        //封装Perssion对象并写进集合，以备写入数据库
        ArrayList<Permission> permissions = new ArrayList<>();
        Arrays.stream(mids).forEach(mid ->permissions.add(new Permission(null,roleId,mid,moduleService.selectByPrimaryKey(mid).getOptValue(),new Date(),new Date())) );
        AssertUtil.isTrue(permissionMapper.insertBatch(permissions) != permissions.size(),"权限数据批量插入失败");

    }
}
