package com.sunmile.crm.controller;

import com.sunmile.crm.base.BaseController;
import com.sunmile.crm.base.ResultInfo;
import com.sunmile.crm.query.CustomerLossQuery;
import com.sunmile.crm.query.CustomerReprieveQuery;
import com.sunmile.crm.service.CustomerLossService;
import com.sunmile.crm.service.CustomerReprieveService;
import com.sunmile.crm.vo.CustomerLoss;
import com.sunmile.crm.vo.CustomerReprieve;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/9 9:46
 * @Description  客户流失 功能contoller类
 * @Modified by Sunmile
 */
@Controller
@RequestMapping("customer_loss")
public class CustomerLossController extends BaseController{

    @Resource
    private CustomerLossService customerLossService;
    
    @Resource
    private CustomerReprieveService customerReprieveService;

    @RequestMapping("index")
    public String index(){
        return "customerLoss/customer_loss";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> list(CustomerLossQuery customerLossQuery){
        return customerLossService.queryByParamsForTable(customerLossQuery);
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping("toCustomerReprPage")
    public String toCustomerReprPage(Integer id, Model model){
        CustomerLoss customerLoss = customerLossService.selectByPrimaryKey(id);
        model.addAttribute("customerLoss",customerLoss);
        return "customerLoss/customer_rep";
    }

    /**
     * 跳转到更新或者添加页面，通过传入的id判断是否为更新操作
     * @param lossId
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("addOrUpdateCustomerReprPage")
    public String addOrUpdateCustomerReprPage(Integer lossId,Integer id,Model model){
        CustomerReprieve customerRep = customerReprieveService.queryCustomerReprieveById(id);
        model.addAttribute("lossId",lossId);
        model.addAttribute("customerRep",customerRep);
        return "customerLoss/customer_rep_add_update";
    }

    /**
     * 依据传入的id和流失原因，进行对应的客户流失记录状态的更新操作
     * @param id 要被记录为流失状态的记录的id
     * @param lossReason 流失原因
     * @return
     */
    @PostMapping("updateCustomerLossStateById")
    @ResponseBody
    public ResultInfo updateCustomerLossStateById(Integer id,String lossReason){
        customerLossService.updateCustomerLossStateById(id,lossReason);
        return success("该条记录确认流失操作成功");
    }

}
