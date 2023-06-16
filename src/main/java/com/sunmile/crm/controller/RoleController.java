package com.sunmile.crm.controller;

import com.sunmile.crm.base.BaseController;
import com.sunmile.crm.base.ResultInfo;
import com.sunmile.crm.query.RoleQuery;
import com.sunmile.crm.service.ModuleService;
import com.sunmile.crm.service.RoleService;
import com.sunmile.crm.vo.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/6 11:47
 * @Description
 * @Modified by Sunmile
 */
@Controller
@RequestMapping("role")
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;
    @Resource
    private ModuleService moduleService;

    /**
     * 添加用户 和 编辑用户时使用
     * 返回指定用户的关联角色信息
     * @param userId 用户id
     * @return 返回查询到的用户分配角色信息
     */
    @ResponseBody
    @PostMapping("queryAllRoles")
    public List<Map<String,Object>> queryAllRoles(Integer userId){
        return roleService.queryAllRoles(userId);
    }

    /**
     * 点击菜单页跳转到对应的页面
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "role/role";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> list(RoleQuery roleQuery){
        return roleService.queryByParamsForTable(roleQuery);
    }

    /**
     * 点击添加角色，展示添加角色frame表单
     * @return 返回 添加角色所在路径页面
     */
    @RequestMapping("addOrUpdateRolePage")
    public String addOrUpdateRolePage(Integer id, Model model){
        Role role = roleService.selectByPrimaryKey(id);
        model.addAttribute("role",role);
        return "role/add_update";
    }

    /**
     * 对角色信息进行新增操作
     * @param role 角色 需要写入数据库中的角色信息
     * @return
     */
    @PostMapping("save")
    @ResponseBody
    public ResultInfo saveRole(Role role){
        roleService.saveRole(role);
        return success("角色信息保存成功");
    }

    /**
     * 对角色信息进行修改操作
     * @param role 角色 需要进行修改的数据库角色信息
     * @return
     */
    @PostMapping("update")
    @ResponseBody
    public ResultInfo updateRole(Role role){
        roleService.updateRole(role);
        return success("角色信息修改成功");
    }

    /**
     * 删除角色信息
     * @param roleId 角色id
     * @return 返回删除结果
     */
    @PostMapping("delete")
    @ResponseBody
    public ResultInfo deleteRole(Integer id){
        roleService.deleteRoleByRoleId(id);
        return success("角色信息删除成功");
    }

    /**
     * 跳转到权限页面
     * @param roleId
     * @param request
     * @return
     */
    @RequestMapping("toAddGrantPage")
    public String toAddGrantPage(Integer roleId, HttpServletRequest request){
        request.setAttribute("roleId",roleId);
        return "role/grant";
    }

    /**
     * 根据前端传过来的角色id和moduleId进行权限的更改
     * @param mids moduleId
     * @param roleId 角色id
     * @return
     */
    @PostMapping("addGrant")
    @ResponseBody
    public ResultInfo addGrant(Integer[] mids,Integer roleId){
        roleService.addGrantByMidsAndRoleId(mids,roleId);
        return success("角色权限添加成功");
    }

}
