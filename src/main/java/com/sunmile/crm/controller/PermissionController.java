package com.sunmile.crm.controller;

import com.sunmile.crm.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/6 22:20
 * @Description
 * @Modified by Sunmile
 */
@Controller
@RequestMapping("permission")
public class PermissionController {
    @Resource
    private PermissionService permissionService;
}
