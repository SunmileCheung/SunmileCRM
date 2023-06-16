package com.sunmile.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sunmile.crm.base.BaseService;
import com.sunmile.crm.dao.CustomerOrderMapper;
import com.sunmile.crm.query.CustomerOrderQuery;
import com.sunmile.crm.util.AssertUtil;
import com.sunmile.crm.vo.CustomerOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/8 11:57
 * @Description
 * @Modified by Sunmile
 */
@Service
public class CustomerOrderService extends BaseService<CustomerOrder,Integer> {

    @Resource
    private CustomerOrderMapper customerOrderMapper;

    /**
     * 依据cus_id查询客户id下对应的订单信息
     * @param customerOrderQuery  查询条件，包含客户id
     * @return 返回结果集
     */
    public Map<String, Object> queryByParamsForTableByCusId(CustomerOrderQuery customerOrderQuery) {
        HashMap<String, Object> result = new HashMap<>();
        AssertUtil.isTrue(Objects.isNull(customerOrderQuery),"查询条件不存在");
        List<CustomerOrder> list = customerOrderMapper.queryListForTableByCusId(customerOrderQuery);
        PageInfo<CustomerOrder> pageInfo =new PageInfo<CustomerOrder>(list);
        result.put("count",pageInfo.getTotal());
        result.put("data",pageInfo.getList());
        result.put("code",0);
        result.put("msg","");
        return result;
    }
}
