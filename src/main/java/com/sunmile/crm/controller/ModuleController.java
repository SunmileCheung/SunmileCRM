package com.sunmile.crm.controller;

import com.sunmile.crm.base.BaseController;
import com.sunmile.crm.base.ResultInfo;
import com.sunmile.crm.service.ModuleService;
import com.sunmile.crm.vo.Module;
import com.sunmile.crm.vo.ZtreeDto;
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
 * @Date 2023/6/6 22:17
 * @Description
 * @Modified by Sunmile
 */
@Controller
@RequestMapping("module")
public class ModuleController extends BaseController {
    @Resource
    private ModuleService moduleService;

    /**
     * 查询并展示roleId角色对应的授权信息
     * @param roleId 角色id
     * @return
     */
    @PostMapping("queryAllModules")
    @ResponseBody
    public List<ZtreeDto> queryAllModules(Integer roleId){
        return moduleService.queryAllModules(roleId);
    }

    /**
     * 点击左侧菜单栏的 菜单管理跳转到菜单页
     * @return
     */
    @RequestMapping("index")
    public String moduleIndex(){
        return "module/module";
    }


    /**
     * 点击左侧菜单栏的菜单管理后展示module数据
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> list(){
        return moduleService.queryAllModulesForList();
    }

    @RequestMapping("addModulePage")
    public String addModulePage(Integer grade,Integer parentId, HttpServletRequest request){
        request.setAttribute("grade",grade);
        request.setAttribute("parentId",parentId);
        return "module/add";
    }

    /**
     * 依据前端传入的数据进行菜单数据的插入操作
     * @param module 菜单数据
     * @return 返回 插入操作的结果
     */
    @PostMapping("save")
    @ResponseBody
    public ResultInfo saveModule(Module module){
        moduleService.saveModule(module);
        return success("module模板保存成功");
    }

    /**
     * 通过修改按钮打开对应的对话框
     * @param id 单条菜单记录的id值
     * @return
     */
    @RequestMapping("updateModulePage")
    public String updateModulePage(Integer id, Model model){
        Module module = moduleService.selectByPrimaryKey(id);
        model.addAttribute("module",module);
        return "module/update";
    }

    /**
     * 执行更新操作
     * @param module
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateModule(Module module){
        moduleService.updateModule(module);
        return success("菜单修改成功");
    }

    /**
     * 依据传入的mid进行菜单的删除操作
     * 删除了菜单后，也要对其关联的资源权限进行删除操作
     * @param mid 菜单id
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteModule(Integer mid){
        moduleService.deleteModuleByMid(mid);
        return success("表单删除成功");
    }
}
