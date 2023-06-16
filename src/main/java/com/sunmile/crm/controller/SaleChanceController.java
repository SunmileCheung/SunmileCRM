package com.sunmile.crm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sunmile.crm.base.BaseController;
import com.sunmile.crm.base.BaseQuery;
import com.sunmile.crm.base.ResultInfo;
import com.sunmile.crm.query.SaleChanceQuery;
import com.sunmile.crm.service.SaleChanceService;
import com.sunmile.crm.util.CookieUtil;
import com.sunmile.crm.util.LoginUserUtil;
import com.sunmile.crm.vo.SaleChance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/4 11:34
 * @Description  营销机会管理的controller层
 * @Modified by Sunmile
 */
@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {

    @Autowired
    private SaleChanceService saleChanceService;

    @RequestMapping("index")
    public String index(){
        return "saleChance/sale_chance";
    }

    /**
     * 展示客户开发计划 和 营销机会管理
     * @param saleChanceQuery
     * @param flag 如果flag = 1 则为查询客户开发计划，否则为营销机会管理
     * @param request
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> querySaleChanceTableByCondition(SaleChanceQuery saleChanceQuery,Integer flag,HttpServletRequest request){
        if (Objects.nonNull(flag) && flag == 1){
            //如果flag取值为1，查询分配给当前用户的客户开发计划
            Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
            System.out.println("从cookie中取出的数据userId数据为:"+userId);
            saleChanceQuery.setAssignMan(userId);
        }
        Map<String,Object> saleChances = saleChanceService.querySaleChanceTableByCondition(saleChanceQuery);
        return saleChances;
    }


    /**
     * 点击添加营销机会或编辑单条记录进行页面跳转展示
     * 如果id传入就展示要进行编辑的单条记录
     * 如果id未传入就进行页面跳转
     * @return
     */
    @RequestMapping("addOrUpdateSaleChancePage")
    public String addOrUpdateSaleChancePage(HttpServletRequest request,Integer id){
        //依据传入的id的值查询对应的单条记录的数据，展示在弹出层以备进行编辑操作
        SaleChance saleChance = saleChanceService.selectByPrimaryKey(id);
        if (Objects.nonNull(saleChance)){
            request.setAttribute("saleChance",saleChance);
        }
        return "saleChance/add_update";
    }


    /**
     * 添加营销机会数据
     * @param request 请求
     * @param saleChance 请求数据，从frema表单传入
     * @return
     */
    @PostMapping("save")
    @ResponseBody
    public ResultInfo save(HttpServletRequest request,SaleChance saleChance){
        String userName = CookieUtil.getCookieValue(request, "userName");
        saleChance.setCreateMan(userName);
        saleChanceService.save(saleChance);
        return success("保存成功");
    }

    /**
     * 依据前端弹出层传入的salechance对象，进行数据的编辑与更新操作
     * 在更新之前首先进行数据的验证和编辑
     * @param saleChance 要进行更新的数据dto
     * @return 返回修改更新结果
     */
    @PostMapping("update")
    @ResponseBody
    public ResultInfo update(SaleChance saleChance){
        saleChanceService.update(saleChance);
        return success("更新成功");
    }

    /**
     * 对销售机会数据进行批量删除
     * @param ids
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    public ResultInfo delete(Integer[] ids){
        return saleChanceService.deleteBatch(ids);
    }

    /**
     * 依据前端传入的销售机会id和要修改的devResult开发状态进行销售机会数据的修改操作
     * @param id 销售机会id
     * @param devResult 销售机会数据对应的开发状态
     * @return 返回json格式的修改结果
     */
    @ResponseBody
    @RequestMapping("updateSaleChanceDevResult")
    public ResultInfo updateSaleChanceDevResult(Integer id,Integer devResult){
        saleChanceService.updateSaleChanceDevResult(id,devResult);
        return success("销售机会状态更新成功");
    }
}
