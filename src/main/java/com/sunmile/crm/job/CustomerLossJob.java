package com.sunmile.crm.job;

import com.sunmile.crm.service.CustomerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/9 10:04
 * @Description
 * @Modified by Sunmile
 */
//@Component
public class CustomerLossJob {


    @Resource
    private CustomerService customerService;

    @Scheduled(cron = "0/2 * * * * ?")
    public void job(){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        customerService.updateCustomerState();
    }
}
