package com.sunmile.crm.controller;

import com.sunmile.crm.base.BaseController;
import com.sunmile.crm.query.CustomerOrderQuery;
import com.sunmile.crm.service.CustomerOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/8 11:55
 * @Description
 * @Modified by Sunmile
 */
@RequestMapping("order")
@Controller
public class CustomerOrderController extends BaseController {

    @Resource
    private CustomerOrderService customerOrderService;

    /**
     * 依据cus_id查询客户id下对应的订单信息
     * @param customerOrderQuery 客户id
     * @return 返回结果集
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> list(CustomerOrderQuery customerOrderQuery){
        Map<String,Object> resultMap = customerOrderService.queryByParamsForTableByCusId(customerOrderQuery);
        return resultMap;
    }
}
