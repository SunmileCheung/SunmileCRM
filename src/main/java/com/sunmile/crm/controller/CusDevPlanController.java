package com.sunmile.crm.controller;

import com.sunmile.crm.base.BaseController;
import com.sunmile.crm.base.ResultInfo;
import com.sunmile.crm.query.CusDevPlanQuery;
import com.sunmile.crm.service.CusDevPlanService;
import com.sunmile.crm.service.SaleChanceService;
import com.sunmile.crm.vo.CusDevPlan;
import com.sunmile.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/5 15:20
 * @Description  客户开发计划 的 controller层
 * @Modified by Sunmile
 */

@RequestMapping("cus_dev_plan")
@Controller
public class CusDevPlanController extends BaseController {
    @Resource
    private CusDevPlanService cusDevPlanService;

    @Resource
    private SaleChanceService saleChanceService;
    @RequestMapping("index")
    public String index(){
        return "/cusDevPlan/cus_dev_plan";
    }

    /**
     * 通过传入的sid跳转到客户开发计划列表
     * @param sid 传入的saleChanceId
     * @param request
     * @return 跳转到客户开发计划页
     */
    @RequestMapping("toCusDevPlanDataPage")
    public String toCusDevPlanDataPage(Integer sid, HttpServletRequest request){
        SaleChance saleChance = saleChanceService.selectByPrimaryKey(sid);
        request.setAttribute("saleChance",saleChance);
        return "cusDevPlan/cus_dev_plan_data";
    }

    /**
     *通过销售机会id查询改销售机会id下的客户开发计划详情
     * @param sid 前端传入的销售机会id
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCusDevPlanBySaleChanceId(CusDevPlanQuery cusDevPlanQuery,Integer sid){
        cusDevPlanQuery.setSaleChanceId(sid);
        return cusDevPlanService.queryCusDevPlanBySaleChanceId(cusDevPlanQuery);
    }


    /**
     * 跳转到对应的添加客户开发计划的页面
     * @param sid 从前端传回的sid  即 营销机会id
     * @param request 请求对象，将sid设置进请求域
     * @return 返回添加客户开发计划的页面，并将sid传递进request作用域
     */
    @RequestMapping("addOrUpdateCusDevPlanPage")
    public String addOrUpdateCusDevPlanPage(Integer sid,Integer id,HttpServletRequest request){
        request.setAttribute("sid",sid);
        CusDevPlan cusDevPlan = cusDevPlanService.selectByPrimaryKey(id);
        request.setAttribute("cusDevPlan",cusDevPlan);
        return "cusDevPlan/add_update";
    }

    /**
     *
     * 1.参数校验
     *    机会id 非空 记录必须存在
     *    计划项内容非空
     *    计划项时间非空
     * 2. 参数默认值
     *    is_valid  1
     *    createDate 系统时间
     *    updateDate  系统时间
     * 3.执行添加 判断结果
     * @param cusDevPlan 要执行插入操作的数据
     * @return 返回插入结果
     */
    @PostMapping("save")
    @ResponseBody
    public ResultInfo save(CusDevPlan cusDevPlan){
        cusDevPlanService.saveCusDevPlan(cusDevPlan);
        return success();
    }

    /**
     * 点击编辑按钮，进行更新操作
     * @param cusDevPlan
     * @return
     */
    @PostMapping("update")
    @ResponseBody
    public ResultInfo update(CusDevPlan cusDevPlan){
        cusDevPlanService.updateCusDevPlan(cusDevPlan);
        return success("客户开发计划更新成功");
    }

    /**
     * 点击删除按钮，依据传入的id进行相应的客户开发计划的逻辑删除操作
     * @param id 客户开发计划对应id
     * @return 返回ResultInfo
     */
    @ResponseBody
    @PostMapping("delete")
    public ResultInfo delete(Integer id){
        cusDevPlanService.deleteCusdevPlan(id);
        return success("该条客户开发计划删除成功");
    }
}
