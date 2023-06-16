package com.sunmile.crm.service;

import com.sunmile.crm.base.BaseService;
import com.sunmile.crm.dao.CustomerLossMapper;
import com.sunmile.crm.util.AssertUtil;
import com.sunmile.crm.vo.CustomerLoss;
import com.sunmile.crm.vo.CustomerReprieve;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/9 9:45
 * @Description
 * @Modified by Sunmile
 */
@Service
public class CustomerLossService extends BaseService<CustomerLoss,Integer> {

    @Resource
    private CustomerLossMapper customerLossMapper;

    /**
     * 依据传入的id和流失原因，进行对应的客户流失记录状态的更新操作
     * @param id 要被记录为流失状态的记录的id
     * @param lossReason 流失原因
     * @return
     */
    public void updateCustomerLossStateById(Integer id, String lossReason) {
        AssertUtil.isTrue(Objects.isNull(id),"前端没有传入id值");
        AssertUtil.isTrue(StringUtils.isBlank(lossReason),"流失原因不能为空");
        CustomerLoss customerLoss = customerLossMapper.selectByPrimaryKeyWithIsValid(id);
        AssertUtil.isTrue(Objects.isNull(customerLoss),"该id在数据库中没有对应的有效数据");
        customerLoss.setUpdateDate(new Date());
        customerLoss.setState(1);
        customerLoss.setConfirmLossTime(new Date());
        customerLoss.setLossReason(lossReason);
        AssertUtil.isTrue(customerLossMapper.updateByPrimaryKeySelective(customerLoss)<1,"流失状态更新失败");
    }
}
