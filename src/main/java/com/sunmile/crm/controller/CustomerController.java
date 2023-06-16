package com.sunmile.crm.controller;

import com.sunmile.crm.base.BaseController;
import com.sunmile.crm.base.ResultInfo;
import com.sunmile.crm.query.CustomerQuery;
import com.sunmile.crm.service.CustomerLinkManService;
import com.sunmile.crm.service.CustomerService;
import com.sunmile.crm.vo.Customer;
import com.sunmile.crm.vo.CustomerLinkMan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/8 10:35
 * @Description
 * @Modified by Sunmile
 */
@Controller
@RequestMapping("customer")
public class CustomerController extends BaseController {
    @Resource
    private CustomerService customerService;

    @Resource
    private CustomerLinkManService customerLinkManService;

    /**
     * 跳转到客户信息页面
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "customer/customer";
    }

    /**
     * 查询客户信息列表
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> list(CustomerQuery customerQuery){
        Map<String, Object> resultMap = customerService.queryByParamsForTable(customerQuery);
        return resultMap;
    }

    @RequestMapping("addOrUpdateCustomerPage")
    public String addOrUpdateCustomerPage(Integer id, Model model){
        Customer customer = customerService.selectByPrimaryKey(id);
        model.addAttribute("customer",customer);
        return "customer/add_update";
    }

    /**
     * 保存用户信息
     * @param customer 从前端传回的用户信息
     * @return 返回保存结果
     */
    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveCustomer(Customer customer){
        customerService.saveCustomer(customer);
        return success("保存用户信息成功");
    }
    /**
     * 修改用户信息
     * @param customer 从前端传回的用户信息
     * @return 返回保存结果
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateCustomer(Customer customer){
        customerService.updateCustomer(customer);
        return success("保存用户信息成功");
    }

    /**
     * 进入查看订单页
     * @param cid 客户id
     * @param model
     * @return
     */
    @RequestMapping("orderInfoPage")
    public String orderInfoPage(Integer cid,Model model){
        Customer customer = customerService.selectByPrimaryKey(cid);
        model.addAttribute("customer",customer);
        return "customer/customer_order";
    }


    /**
     * 订单详情页展示
     * @param orderId 通过订单id查询 订单详情
     * @param model
     * @return
     */
    @RequestMapping("orderDetailPage")
    public String orderDetailPage(Integer orderId,Model model){
        Map<String,Object> orderMap = customerService.queryOrderDetailPageByOrderId(orderId);
        model.addAttribute("order",orderMap);
        return "customer/customer_order_detail";
    }

    /**
     * 逻辑删除客户信息
     * @param id 客户id
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo delete(Integer id){
        customerService.logicDeleteCustomByCusId(id);
        return success("客户信息删除成功");
    }

    /**
     * 客户贡献分析
     * @return
     */
    @RequestMapping("queryCustomerContributionByParams")
    @ResponseBody
    public Map<String,Object> queryCustomerContributionByParams(CustomerQuery customerQuery){
        return customerService.queryCustomerContributionByParams(customerQuery);
    }

    /**
     * 客户构成分析
     * @return 返回客户构成数据
     */
    @RequestMapping("countCutomerMake")
    @ResponseBody
    public Map<String,Object> countCutomerMake(){
        Map<String, Object> stringObjectMap = customerService.countCustoerMake();
        return stringObjectMap;
    }

    @RequestMapping("countCutomerMake02")
    @ResponseBody
    public Map<String,Object> countCutomerMake02(){
        Map<String, Object> stringObjectMap = customerService.countCutomerMake02();
        return stringObjectMap;
    }

    /**
     * 展示联系人信息，如果没有联系人信息，默认为添加操作展示添加页面
     * @param cid  客户id
     * @param model
     * @return
     */
    @RequestMapping("linkManInfo")
    public String linkManInfo(Integer cid,Model model){
        CustomerLinkMan customerLinkMan = customerLinkManService.selectByCustomerId(cid);
        model.addAttribute("customerlinkman",customerLinkMan);
        return "customer/link_man_info";
    }


    /**
     * 保存联系人信息
     * @param customerLinkMan 联系人信息
     * @return
     */
    @PostMapping("saveLinkManInfo")
    @ResponseBody
    public ResultInfo saveLinkManInfo(CustomerLinkMan customerLinkMan){
        customerLinkManService.saveLinkManInfo(customerLinkMan);
        return success("保存联系人信息成功");
    }

    @PostMapping("updateLinkManInfo")
    @ResponseBody
    public ResultInfo updateLinkManInfo(CustomerLinkMan customerLinkMan){
        customerLinkManService.updateLinkManInfo(customerLinkMan);
        return success("更新联系人信息成功");
    }


}
