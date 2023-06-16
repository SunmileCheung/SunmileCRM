package com.sunmile.crm.controller;

import com.sunmile.crm.base.BaseController;
import com.sunmile.crm.base.ResultInfo;
import com.sunmile.crm.query.CustomerReprieveQuery;
import com.sunmile.crm.service.CustomerReprieveService;
import com.sunmile.crm.vo.CustomerReprieve;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/9 11:01
 * @Description
 * @Modified by Sunmile
 */
@Controller
@RequestMapping("customer_rep")
public class CustomerReprieveController extends BaseController {
    @Resource
    private CustomerReprieveService customerReprieveService;

    /**
     * customer_rep页面的表格展示
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> list(CustomerReprieveQuery customerReprieveQuery){
        return customerReprieveService.queryByParamsForTable(customerReprieveQuery);
    }

    /**
     * 添加CustomerReprieve信息
     * @param customerReprieve 要被添加的对象信息
     * @return
     */
    @PostMapping("save")
    @ResponseBody
    public ResultInfo save(CustomerReprieve customerReprieve){
        customerReprieveService.saveCustomerReprieve(customerReprieve);
        return success("添加customerReprieve信息成功");
    }

    /**
     * 更新CustomerReprieve信息
     * @param customerReprieve 要被更新的对象信息
     * @return
     */
    @PostMapping("update")
    @ResponseBody
    public ResultInfo update(CustomerReprieve customerReprieve){
        customerReprieveService.updateCustomerReprieve(customerReprieve);
        return success("更新customerReprieve信息成功");
    }

    /**
     * 删除某项暂缓措施
     * @param id 被删除的暂缓措施的id
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    public ResultInfo delete(Integer id){
        customerReprieveService.logicDeleteCustomerRep(id);
        return success("删除某项暂缓措施成功");
    }

}
