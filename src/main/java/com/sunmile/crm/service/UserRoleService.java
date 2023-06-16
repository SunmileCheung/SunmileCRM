package com.sunmile.crm.service;

import com.sunmile.crm.base.BaseService;
import com.sunmile.crm.dao.UserRoleMapper;
import com.sunmile.crm.vo.UserRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/6 15:56
 * @Description
 * @Modified by Sunmile
 */
@Service
public class UserRoleService extends BaseService<UserRole,Integer> {
    @Resource
    private UserRoleMapper userRoleMapper;
}
