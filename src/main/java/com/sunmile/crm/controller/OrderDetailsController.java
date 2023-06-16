package com.sunmile.crm.controller;

import com.sunmile.crm.query.OrderDetailQuery;
import com.sunmile.crm.service.OrderDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/8 16:54
 * @Description
 * @Modified by Sunmile
 */

@Controller
@RequestMapping("order_details")
public class OrderDetailsController {
    @Resource
    private OrderDetailsService orderDetailsService;
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object>  listForTheLayUiTable(OrderDetailQuery orderDetailQuery){
        return orderDetailsService.queryByParamsForTable(orderDetailQuery);
    }
}
