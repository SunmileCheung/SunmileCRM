package com.sunmile.crm.dao;

import com.sunmile.crm.base.BaseMapper;
import com.sunmile.crm.vo.Module;
import com.sunmile.crm.vo.ZtreeDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ModuleMapper extends BaseMapper<Module,Integer> {

    /**
     * 查询并展示roleId角色对应的授权信息
     * @param roleId 角色id
     * @return
     */
    List<ZtreeDto> queryAllModules();

    List<Module> queryAllModulesForList();


    Module queryModuleInfoByModuleNameAndGrade(@Param("moduleName") String moduleName,@Param("grade") Integer grade);

    Module queryModuleInfoByModuleInfoAndGrade(@Param("url") String url, @Param("grade") Integer grade);

    Module queryModuleByOptValue(String optValue);

    /**
     * 依据传入的mid查询其下的子菜单数量
     * @param mid moduleId
     * @return 返回数量
     */
    Integer queryModuleByParentId(Integer mid);
}