package com.sunmile.crm.service;

import com.sunmile.crm.base.BaseService;
import com.sunmile.crm.dao.CustomerReprieveMapper;
import com.sunmile.crm.util.AssertUtil;
import com.sunmile.crm.vo.CustomerReprieve;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/9 11:00
 * @Description
 * @Modified by Sunmile
 */
@Service
public class CustomerReprieveService extends BaseService<CustomerReprieve,Integer> {

    @Resource
    private CustomerReprieveMapper customerReprieveMapper;

    /**
     * 依据lossId查询CustomerReprieve记录
     * @param lossId
     * @return
     */
    public CustomerReprieve queryCustomerReprieveById(Integer id) {
        //AssertUtil.isTrue(Objects.isNull(id),"id不能为空");
        CustomerReprieve customerReprieve = customerReprieveMapper.queryCustomerReprieveById(id);
        return customerReprieve;
    }

    /**
     * 添加CustomerReprieve信息
     * @param customerReprieve 要被添加的对象信息
     * @return
     */
    public void saveCustomerReprieve(CustomerReprieve customerReprieve) {
        AssertUtil.isTrue(StringUtils.isBlank(customerReprieve.getMeasure()),"暂缓措施不能为空");
        AssertUtil.isTrue(Objects.isNull(customerReprieve.getLossId()),"lossId不能为空");
        customerReprieve.setCreateDate(new Date());
        customerReprieve.setUpdateDate(new Date());
        customerReprieve.setIsValid(1);
        AssertUtil.isTrue(customerReprieveMapper.insertSelective(customerReprieve) < 1,"数据添加失败");
    }
    /**
     * 更新CustomerReprieve信息
     * @param customerReprieve 要被更新的对象信息
     * @return
     */
    public void updateCustomerReprieve(CustomerReprieve customerReprieve) {
        AssertUtil.isTrue(StringUtils.isBlank(customerReprieve.getMeasure()),"暂缓措施不能为空");
        AssertUtil.isTrue(Objects.isNull(customerReprieve.getLossId()),"lossId不能为空");
        AssertUtil.isTrue(Objects.isNull(customerReprieve.getId()),"customerRepreieveId不能为空");
        CustomerReprieve tmp1 = customerReprieveMapper.selectByPrimaryKey(customerReprieve.getId());
        AssertUtil.isTrue(Objects.isNull(tmp1),"该id对应的数据在数据库中不存在");
        customerReprieve.setIsValid(1);
        customerReprieve.setUpdateDate(new Date());
        AssertUtil.isTrue(customerReprieveMapper.updateByPrimaryKeySelective(customerReprieve) < 1,"数据更新失败");

    }
    /**
     * 删除某项暂缓措施
     * @param id 被删除的暂缓措施的id
     * @return
     */
    public void logicDeleteCustomerRep(Integer id) {
        AssertUtil.isTrue(Objects.isNull(id),"暂缓措施项的id不存在");
        CustomerReprieve customerReprieve = customerReprieveMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(Objects.isNull(customerReprieve),"要被删除的暂缓措施项在数据库中不存在");
        customerReprieve.setIsValid(0);
        AssertUtil.isTrue(customerReprieveMapper.updateByPrimaryKeySelective(customerReprieve) < 1,"删除数据失败");
    }


}
