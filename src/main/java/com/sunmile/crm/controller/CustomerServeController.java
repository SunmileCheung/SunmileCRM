package com.sunmile.crm.controller;

import com.sunmile.crm.base.BaseController;
import com.sunmile.crm.base.ResultInfo;
import com.sunmile.crm.query.CustomerServeQuery;
import com.sunmile.crm.service.CustomerServeService;
import com.sunmile.crm.util.CookieUtil;
import com.sunmile.crm.util.LoginUserUtil;
import com.sunmile.crm.vo.CustomerServe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/9 16:09
 * @Description
 * @Modified by Sunmile
 */
@Controller
@RequestMapping("customer_serve")
public class CustomerServeController extends BaseController {

    @Resource
    private CustomerServeService customerServeService;

    /**
     * main页面跳转到相应的服务页面
     * @param type 要进行跳转的页面类型
     * @return
     */
    @GetMapping("index/{type}")
    public String index(@PathVariable Integer type){
        if (Objects.nonNull(type)){
            if ( type == 1){
                //服务创建
                return "customerServe/customer_serve";
            } else if (type == 2) {
                //服务分配
                return "customerServe/customer_serve_assign";
            } else if (type == 3) {
                //服务处理
                return "customerServe/customer_serve_proce";
            } else if (type == 4) {
                //服务反馈
                return "customerServe/customer_serve_feed_back";
            } else if (type == 5) {
                //服务归档
                return "customerServe/customer_serve_archive";
            }
        }
        return "main";
    }

    /**
     * 客户服务页展示内容
     * @param customerServeQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> list(CustomerServeQuery customerServeQuery, Integer flag, HttpServletRequest request){
        if (Objects.nonNull(flag) && flag == 1){
            customerServeQuery.setAssigner(LoginUserUtil.releaseUserIdFromCookie(request));
        }
        return customerServeService.queryByParamsForTable(customerServeQuery);
    }

    /**
     * 跳转到添加界面
     * @return
     */
    @RequestMapping("addCustomerServePage")
    public String addCustomerServePage(){
        return "customerServe/customer_serve_add";
    }

    /**
     * 添加服务
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveCustomerServe(CustomerServe customerServe){
        customerServeService.saveCustomerServe(customerServe);
        return success("保存服务成功");
    }

    /**
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("addCustomerServeAssignPage")
    public String addCustomerServeAssignPage(Integer id, Model model){
        CustomerServe customerServe = customerServeService.selectByPrimaryKeyWithValid(id);
        model.addAttribute("customerServe",customerServe);
        return "customerServe/customer_serve_assign_add";
    }

    /**
     * 将某项服务的分配相关信息进行更新
     * @param customerServe 待更新的customerServe信息对象
     * @return
     */
    @PostMapping("update")
    @ResponseBody
    public ResultInfo updateCustomerServer(CustomerServe customerServe){
        customerServeService.updateCustomerServer(customerServe);
        return success("服务分配成功");
    }


    /**
     * 打开服务处理页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("addCustomerServeProcePage")
    public String addCustomerServeProcePage(Integer id,Model model){
        CustomerServe customerServe = customerServeService.selectByPrimaryKeyWithValid(id);
        model.addAttribute("customerServe",customerServe);
        return "customerServe/customer_serve_proce_add";
    }

    @RequestMapping("addCustomerServeBackPage")
    public String addCustomerServeBackPage(Integer id,Model model){
        CustomerServe customerServe = customerServeService.selectByPrimaryKeyWithValid(id);
        model.addAttribute("customerServe",customerServe);
        return "customerServe/customer_serve_feed_back_add";
    }

    @RequestMapping("queryCustomerServeInfo")
    @ResponseBody
    public Map<String,Object> queryCustomerServeInfo(CustomerServeQuery customerServeQuery){
        return customerServeService.queryCustomerServeInfo(customerServeQuery);
    }

    /**
     * 查询并提供数据报表的信息
     * 以客户明为维度的统计报表数据
     * @return
     */
    @PostMapping("customerServeInfo")
    @ResponseBody
    public Map<String,Object> customerServeInfo(){
        Map<String, Object> stringObjectMap = customerServeService.customerServeInfo();
        return stringObjectMap;
    }
}
