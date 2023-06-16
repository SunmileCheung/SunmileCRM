package com.sunmile.crm.service;

import com.sunmile.crm.base.BaseService;
import com.sunmile.crm.dao.OrderDetailsMapper;
import com.sunmile.crm.vo.OrderDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/8 16:53
 * @Description
 * @Modified by Sunmile
 */
@Service
public class OrderDetailsService extends BaseService<OrderDetails,Integer> {

    @Resource
    private OrderDetailsMapper orderDetailsMapper;
}
