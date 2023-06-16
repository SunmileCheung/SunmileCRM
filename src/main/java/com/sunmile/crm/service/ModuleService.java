package com.sunmile.crm.service;

import com.sunmile.crm.base.BaseService;
import com.sunmile.crm.dao.ModuleMapper;
import com.sunmile.crm.util.AssertUtil;
import com.sunmile.crm.vo.Module;
import com.sunmile.crm.vo.ZtreeDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/6 22:16
 * @Description
 * @Modified by Sunmile
 */
@Service
public class ModuleService extends BaseService<Module,Integer> {
    @Resource
    private ModuleMapper moduleMapper;
    @Autowired
    private PermissionService permissionService;

    /**
     * 查询并展示roleId角色对应的授权信息
     * @param roleId 角色id
     * @return
     */
    public List<ZtreeDto> queryAllModules(Integer roleId) {
        List<ZtreeDto> ztreeDtos = moduleMapper.queryAllModules();
        //从权限表中查出所有的已经赋予了角色权限的权限id
        List<Integer> moduleIds = permissionService.queryRoleHasAllModuleIdsByRoleId(roleId);
        ztreeDtos.forEach(ztree -> {
            //遍历结果集并判断权限是否被选中
            if (moduleIds.contains(ztree.getId())) {
                ztree.setChecked(true);
            }
        });
        return ztreeDtos;
    }

    /**
     * 展示所有的module数据
     * 检索数据用于展示在菜单管理界面
     * @return
     */
    public Map<String,Object> queryAllModulesForList(){
        HashMap<String, Object> resultMap = new HashMap<>();
        List<Module> modules = moduleMapper.queryAllModulesForList();
        resultMap.put("count",modules.size());
        resultMap.put("code",0);
        resultMap.put("msg","success");
        resultMap.put("data",modules);
        return resultMap;
    }


    /**
     *
     * 1.参数校验
     *     菜单名
     *         非空 同一层级 菜单名唯一
     *     url
     *        二级菜单时 非空 不可重复
     *     上级菜单 parentId
     *        一级菜单  parentId (-1)
     *        二级|三级菜单   parentId 非空 上级菜单记录必须存在
     *     菜单层级  grade
     *       非空  0|1|2
     *     权限码  optValue
     *        非空  不可重复
     * 2.参数默认值设置
     *      isValid   createDate  updateDate
     * 3.执行添加 判断结果
     *
     * @param module
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveModule(Module module) {
        //非空 同一层级 菜单名唯一
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()),"菜单名称不能为空");
        Module tmp1 = moduleMapper.queryModuleInfoByModuleNameAndGrade(module.getModuleName(),module.getGrade());
        AssertUtil.isTrue(Objects.nonNull(tmp1),"同一层级存在同名菜单");
        if (module.getGrade() == 1){
            // 二级菜单时 非空 不可重复
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()),"二级菜单Url信息为必填项！");
            Module tmp2 = moduleMapper.queryModuleInfoByModuleInfoAndGrade(module.getUrl(),module.getGrade());
            AssertUtil.isTrue(Objects.nonNull(tmp2),"二级菜单下该Url已存在");
        }
        /**
         * 上级菜单 parentId
         *    一级菜单  parentId (-1)
         *    二级|三级菜单   parentId 非空 上级菜单记录必须存在
         */
        if (module.getGrade() == 0){
            module.setParentId(-1);
        }else {
            AssertUtil.isTrue(Objects.isNull(module.getParentId()),"二三级菜单下parentId不能为空");
            //将父级id传入，作为主键条件进行查询操作，判断是否存在记录
            Module tmp3 = moduleMapper.selectByPrimaryKey(module.getParentId());
            AssertUtil.isTrue(Objects.isNull(tmp3),"指定的parentId对应的菜单数据记录不存在");
        }
        /**
         * 菜单层级  grade
         *   非空  0|1|2
         * 权限码  optValue
         *    非空  不可重复
         */
        AssertUtil.isTrue(Objects.isNull(module.getGrade())
                || !(module.getGrade() == 0 || module.getGrade() ==1 ||module.getGrade() ==2),"grade层级数据不符合要求");
        AssertUtil.isTrue(Objects.isNull(module.getOptValue()),"权限码不能为空");
        Module tmp4 = moduleMapper.queryModuleByOptValue(module.getOptValue());
        AssertUtil.isTrue(Objects.nonNull(tmp4),"权限码已经存在");
        /**
         *  2.参数默认值设置
         *       isValid   createDate  updateDate
         */
        module.setIsValid((byte)1);
        module.setUpdateDate(new Date());
        module.setCreateDate(new Date());
        AssertUtil.isTrue(moduleMapper.insertSelective(module) < 1,"数据插入失败");
    }

    /**
     * 1.参数校验
     *    id 记录必须存在
     *     菜单名
     *         非空 同一层级 菜单名唯一
     *     url
     *        二级菜单时 非空 不可重复
     *     上级菜单 parentId
     *        二级|三级菜单   parentId 非空 上级菜单记录必须存在
     *     菜单层级  grade
     *       非空  0|1|2
     *     权限码  optValue
     *        非空  不可重复
     * 2.参数默认值设置
     *      updateDate
     * 3.执行更新 判断结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateModule(Module module) {

        /**
         *  id 记录必须存在
         *   菜单名
         *       非空 同一层级 菜单名唯一
         */
        AssertUtil.isTrue(Objects.isNull(module.getId()),"id不能为空");
        Module tmp1 = moduleMapper.selectByPrimaryKey(module.getId());
        AssertUtil.isTrue(Objects.isNull(tmp1),"该条菜单记录不存在");
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()),"菜单名不能为空");
        Module tmp2 = moduleMapper.queryModuleInfoByModuleNameAndGrade(module.getModuleName(), module.getGrade());
        AssertUtil.isTrue(Objects.nonNull(tmp2) && !tmp2.getId().equals(module.getId()),"同一层级下存在同名菜单");
        /**
         * url
         *    二级菜单时 非空 不可重复
         */
        if (module.getGrade() == 1){
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()),"二级菜单的url不能为空");
            Module tmp3 = moduleMapper.queryModuleInfoByModuleInfoAndGrade(module.getUrl(), module.getGrade());
            AssertUtil.isTrue(Objects.nonNull(tmp3) && !tmp3.getId().equals(module.getId()),"同级菜单下存在相同的url");
        }
        /**
         * 上级菜单 parentId
         *    二级|三级菜单   parentId 非空 上级菜单记录必须存在
         */
        if (module.getGrade() == 1 || module.getGrade() == 2){
            AssertUtil.isTrue(Objects.isNull(module.getParentId()),"二三级菜单必须存在父id");
            Module tmp4 = moduleMapper.selectByPrimaryKey(module.getParentId());
            AssertUtil.isTrue(Objects.isNull(tmp4),"传入的parentId对应的父菜单不存在");
        }
        /**
         * 菜单层级  grade
         *   非空  0|1|2
         */
        Integer grade = module.getGrade();
        AssertUtil.isTrue(Objects.isNull(grade) || !(grade ==0 || grade == 1|| grade ==2),"层级记录不合法");
        /**
         * 权限码  optValue
         *    非空  不可重复
         */
        String optValue = module.getOptValue();
        AssertUtil.isTrue(StringUtils.isBlank(optValue),"权限码不能为空");
        Module tmp5 = moduleMapper.queryModuleByOptValue(optValue);
        AssertUtil.isTrue(Objects.isNull(tmp5),"该权限码已存在");
        /**
         * 2.参数默认值设置
         *      updateDate
         * 3.执行更新 判断结果
         */
        module.setUpdateDate(new Date());
        AssertUtil.isTrue(moduleMapper.updateByPrimaryKeySelective(module) < 1,"更新操作执行失败");

    }
    /**
     * 依据传入的mid进行菜单的删除操作
     * 删除了菜单后，也要对其关联的资源权限进行删除操作
     * @param mid 菜单id
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteModuleByMid(Integer mid) {

        /**
         * 1.记录必须存在
         *     id 非空  记录存在
         * 2.如果待删除的菜单存在子菜单  不允许直接删除当前菜单
         * 3.如果删除的菜单 在权限表中存在对应记录  此时要级联删除权限表对应记录
         */
        AssertUtil.isTrue(Objects.isNull(mid),"传入的mid不能为空");
        Module module = moduleMapper.selectByPrimaryKey(mid);
        AssertUtil.isTrue(Objects.isNull(module),"该mid对应的module信息不存在");
        Integer count = moduleMapper.queryModuleByParentId(mid);
        AssertUtil.isTrue(count > 0,"该mid下存在子菜单，请先删除子菜单");
        //满足以上条件后，要删除module记录还需要删除权限表的记录
        Integer permissionCount = permissionService.queryPermissonCountByMid(mid);
        Integer row = permissionService.deletePermissionByMid(mid);
        AssertUtil.isTrue(row != permissionCount ,"删除菜单时关联删除权限操作失败");
        Module module1 = new Module();
        module1.setId(mid);
        module1.setIsValid((byte)0);
        AssertUtil.isTrue(moduleMapper.updateByPrimaryKeySelective(module1) < 1,"删除菜单操作失败");

    }
}
