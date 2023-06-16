package com.sunmile.crm.service;

import com.sunmile.crm.base.BaseService;
import com.sunmile.crm.dao.CustomerContactMapper;
import com.sunmile.crm.util.AssertUtil;
import com.sunmile.crm.vo.CustomerContact;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/11 15:42
 * @Description
 * @Modified by Sunmile
 */
@Service
public class CustomerContactService extends BaseService<CustomerContact,Integer> {

    @Resource
    private CustomerContactMapper customerContactMapper;

    /**
     * 保存交往记录信息
     * @param customerContact
     * @return
     */
    public  void saveCustomerContact(CustomerContact customerContact) {
        checkParamForSaveOrUpdateCusContact(customerContact);
        customerContact.setIsValid(1);
        customerContact.setContactTime(new Date());
        customerContact.setCreateDate(new Date());
        AssertUtil.isTrue(customerContactMapper.insertSelective(customerContact)<1,"插入操作失败");

    }

    /**
     * 对参数进行必要的校验操作 并给更新时间字段进行赋值操作
     * @param customerContact
     */
    private void checkParamForSaveOrUpdateCusContact(CustomerContact customerContact) {
        AssertUtil.isTrue(StringUtils.isBlank(customerContact.getAddress()),"地址信息不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(customerContact.getOverview()),"预览信息不能为空");
        customerContact.setUpdateDate(new Date());
    }

    /**
     * 更新交往记录信息
     * @param customerContact
     * @return
     */
    public void updateCustomerContact(CustomerContact customerContact) {
        checkParamForSaveOrUpdateCusContact(customerContact);
        AssertUtil.isTrue(customerContactMapper.updateByPrimaryKeySelective(customerContact) < 1,"交往记录信息更新失败");
    }


    /**
     *   依据cid查询来往记录
     * @param cid
     * @return
     */
    public CustomerContact selectCustomerContactByCid(Integer cid) {
        AssertUtil.isTrue(Objects.isNull(cid),"客户id不能为空");
        CustomerContact customerContact = customerContactMapper.selectCustomerContactByCid(cid);
        return customerContact;
    }

    /**
     * 删除客户交往记录
     * @param id
     * @return
     */
    public void deleteCustomerContactById(Integer id) {
        AssertUtil.isTrue(Objects.isNull(id),"交往记录id不能为空");
        CustomerContact customerContact = customerContactMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(Objects.isNull(customerContact),"这条交往记录不存在");
        customerContact.setIsValid(0);
        AssertUtil.isTrue(customerContactMapper.updateByPrimaryKeySelective(customerContact)<1,"删除交往记录失败");

    }
}
