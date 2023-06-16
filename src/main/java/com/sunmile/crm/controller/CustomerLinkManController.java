package com.sunmile.crm.controller;

import com.sunmile.crm.base.BaseController;
import com.sunmile.crm.service.CustomerLinkManService;
import com.sunmile.crm.vo.CustomerLinkMan;

import javax.annotation.Resource;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/11 13:48
 * @Description
 * @Modified by Sunmile
 */
public class CustomerLinkManController extends BaseController {

    @Resource
    private CustomerLinkManService customerLinkManService;
}
