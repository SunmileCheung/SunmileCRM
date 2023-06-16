package com.sunmile.crm.controller;

import com.sunmile.crm.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/10 10:33
 * @Description
 * @Modified by Sunmile
 */
@Controller
public class CustomerReportController extends BaseController {


    @RequestMapping("report/{type}")
    public String index(@PathVariable Integer type){
        if (Objects.nonNull(type)){
            if (type == 0){
                return "report/customer_contri";
            } else if (type == 1) {
                return "report/customer_make";
            } else if (type == 2) {
                return "report/customer_serve";
            } else if (type == 3) {
                return "report/customer_loss";
            }else {
                return "main";
            }
        }else {
            return "main";
        }

    }
}
