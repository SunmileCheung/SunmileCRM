package com.sunmile.crm.controller;

import com.sunmile.crm.base.BaseController;
import com.sunmile.crm.base.ResultInfo;
import com.sunmile.crm.query.CustomerContactQuery;
import com.sunmile.crm.query.CustomerQuery;
import com.sunmile.crm.service.CustomerContactService;
import com.sunmile.crm.vo.CustomerContact;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/11 15:46
 * @Description
 * @Modified by Sunmile
 */
@Controller
@RequestMapping("customer_concat")
public class CustomerContactController extends BaseController {

    @Resource
    private CustomerContactService customerContactService;

    //依据cid查询来往记录
    @RequestMapping("to_customer_contact")
    public String linkManInfo(Integer cid, Model model){
       /* CustomerContact customerContact = customerContactService.selectCustomerContactByCid(cid);
        model.addAttribute("customerContact",customerContact);*/
        model.addAttribute("cid",cid);
        return "customer/customer_contact";
    }

    /**
     * 保存交往记录信息
     * @param customerContact
     * @return
     */
    @PostMapping("saveCustomerContact")
    @ResponseBody
    public ResultInfo saveCustomerContact(CustomerContact customerContact){
        customerContactService.saveCustomerContact(customerContact);
        return success("交往记录信息保存成功");
    }

    /**
     * 更新交往记录信息
     * @param customerContact
     * @return
     */
    @PostMapping("updateCustomerContact")
    @ResponseBody
    public ResultInfo updateCustomerContact(CustomerContact customerContact){
        customerContactService.updateCustomerContact(customerContact);
        return success("交往记录信息更新成功");
    }


    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> listCustomerContact(CustomerContactQuery customerContactQuery){
        Map<String, Object> resultMap = customerContactService.queryByParamsForTable(customerContactQuery);
        return resultMap;
    }


    @RequestMapping("addOrUpdateCustomerContact")
    public String addOrUpdateCustomerContact(Integer id,String cusId,Model model){
        System.out.println(id);
        System.out.println(cusId);
        CustomerContact customerContact = customerContactService.selectByPrimaryKey(id);
        model.addAttribute("customerContact",customerContact);
        model.addAttribute("cusId",cusId);
        return "customer/customer_contact_update";
    }

    @PostMapping("add")
    @ResponseBody
    public ResultInfo addCustomerContact(CustomerContact customerContact){
        customerContactService.saveCustomerContact(customerContact);
        return success("添加成功");
    }

    @PostMapping("update")
    @ResponseBody
    public ResultInfo updateCustomerContact02(CustomerContact customerContact){
        customerContactService.updateCustomerContact(customerContact);
        return success("修改成功");
    }

    /**
     * 删除客户交往记录
     * @param id
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    public ResultInfo deleteCustomerContact(Integer id){
        customerContactService.deleteCustomerContactById(id);
        return success("删除成功");
    }

}
