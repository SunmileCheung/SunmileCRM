package com.sunmile.crm.service;

import com.sunmile.crm.base.BaseService;
import com.sunmile.crm.dao.CustomerLinkManMapper;
import com.sunmile.crm.util.AssertUtil;
import com.sunmile.crm.util.PhoneUtil;
import com.sunmile.crm.vo.CustomerLinkMan;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/11 13:47
 * @Description
 * @Modified by Sunmile
 */
@Service
public class CustomerLinkManService extends BaseService<CustomerLinkMan,Integer> {

    @Resource
    private CustomerLinkManMapper customerLinkManMapper;

    public CustomerLinkMan selectByCustomerId(Integer cid) {
        AssertUtil.isTrue(Objects.isNull(cid),"cid不能为空");
        CustomerLinkMan customerLinkMan = customerLinkManMapper.selectByCustomerId(cid);
        return customerLinkMan;
    }

    /**
     * 保存联系人信息
     * @param customerLinkMan 联系人信息
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveLinkManInfo(CustomerLinkMan customerLinkMan) {
        checkParamsOfCustomerLinkMan(customerLinkMan);
        CustomerLinkMan temp1 = customerLinkManMapper.queryCustomerLinkManByLinkName(customerLinkMan.getLinkName());
        AssertUtil.isTrue(temp1 != null,"该名称已存在");
        customerLinkMan.setCeateDate(new Date());
        customerLinkMan.setIsValid(1);
        customerLinkMan.setUpdateDate(new Date());
        AssertUtil.isTrue(customerLinkManMapper.insertSelective(customerLinkMan)<1,"联系人信息插入失败");
    }

    /**
     * 保存客户联系人信息
     * @param customerLinkMan 客户联系人
     */
    private void checkParamsOfCustomerLinkMan(CustomerLinkMan customerLinkMan) {
        AssertUtil.isTrue(StringUtils.isBlank(customerLinkMan.getLinkName()),"linkName不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(customerLinkMan.getSex()),"性别信息不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(customerLinkMan.getZhiwei()),"职位信息不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(customerLinkMan.getPhone()),"联系电话不能为空");
        AssertUtil.isTrue(!PhoneUtil.isMobile(customerLinkMan.getPhone()),"手机号码格式不正确");
        AssertUtil.isTrue(StringUtils.isBlank(customerLinkMan.getOfficePhone()),"办公室电话不能为空");
    }

    /**
     * 更新联系人信息
     * @param customerLinkMan
     */
   @Transactional(propagation = Propagation.REQUIRED)
    public void updateLinkManInfo(CustomerLinkMan customerLinkMan) {
        checkParamsOfCustomerLinkMan(customerLinkMan);
        CustomerLinkMan temp1 = customerLinkManMapper.queryCustomerLinkManByLinkName(customerLinkMan.getLinkName());
        AssertUtil.isTrue(Objects.nonNull(temp1) && !temp1.getCusId().equals(customerLinkMan.getId()),"您要修改为的linkName在数据库中已存在");
        customerLinkMan.setUpdateDate(new Date());
        AssertUtil.isTrue(customerLinkManMapper.updateByPrimaryKeySelective(customerLinkMan) < 1,"联系人信息修改失败");
    }
}
