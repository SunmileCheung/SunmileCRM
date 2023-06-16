package com.sunmile.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sunmile.crm.base.BaseService;
import com.sunmile.crm.dao.CustomerMapper;
import com.sunmile.crm.dao.CustomerServeMapper;
import com.sunmile.crm.enums.CustomerStateEnum;
import com.sunmile.crm.query.CustomerServeQuery;
import com.sunmile.crm.util.AssertUtil;
import com.sunmile.crm.vo.Customer;
import com.sunmile.crm.vo.CustomerServe;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/9 16:08
 * @Description
 * @Modified by Sunmile
 */
@Service
public class CustomerServeService extends BaseService<CustomerServe,Integer> {
    @Resource
    private CustomerServeMapper customerServeMapper;
    @Resource
    private CustomerMapper customerMapper;

    /**
     * 添加服务
     * @return
     */
    public void saveCustomerServe(CustomerServe customerServe) {
        //进行必要的参数校验
        checkCustomerServeInfoParam(customerServe);
        //进行参数的默认赋值操作
        customerServe.setIsValid(1);
        //创建时间默认为当前时间
        customerServe.setCreateDate(new Date());
        customerServe.setState(CustomerStateEnum.CREATION.getCode());
        customerServe.setUpdateDate(new Date());
        AssertUtil.isTrue(customerServeMapper.insertSelective(customerServe) < 1,"服务信息插入失败");
    }

    /**
     * 对customerServe进行必要的参数校验
     * @param customerServe
     */
    private void checkCustomerServeInfoParam(CustomerServe customerServe) {
        AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServeType()),"服务类型不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(customerServe.getCustomer()),"客户名不能为空");
        Customer customer = customerMapper.queryCustomerInfoByCustomerName(customerServe.getCustomer());
        AssertUtil.isTrue(Objects.isNull(customer),"客户名在数据库中没有真实存在");
        AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceRequest()),"服务内容不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(customerServe.getOverview()),"服务概要不能为空");
    }

    //查询数据库中的有效数据 is_valid为1
    public CustomerServe selectByPrimaryKeyWithValid(Integer id) {
        return customerServeMapper.selectByPrimaryKeyWithValid(id);
    }

    /**
     * 将某项服务的分配相关信息进行更新
     * @param customerServe 待更新的customerServe信息对象
     * @return
     */
    public void updateCustomerServer(CustomerServe customerServe) {
        checkCustomerServeInfoParam(customerServe);
        //首先将更新时间进行更新
        customerServe.setUpdateDate(new Date());
        if (CustomerStateEnum.ASSIGN.getCode().equals(customerServe.getState())){
            //判断指派人是否传入
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getAssigner()),"指派人信息不能为空");
            //更新指派时间
            customerServe.setAssignTime(new Date());
            //更新state状态
            //customerServe.setState(CustomerStateEnum.PROCE.getCode());
            //进行更新操作
            AssertUtil.isTrue(customerServeMapper.updateByPrimaryKeySelective(customerServe) < 1,"服务分配失败");

        } else if (CustomerStateEnum.PROCE.getCode().equals(customerServe.getState())) {
            updateCustomerServerForProce(customerServe);
        } else if (CustomerStateEnum.FEEDBACK.getCode().equals(customerServe.getState())) {
            updateCustomerServerForFeedBack(customerServe);
        }

    }

    //当当前状态为需要进行归档操作时进行的操作
    private void updateCustomerServerForFeedBack(CustomerServe customerServe) {
        AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProceResult()),"处理结果不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(customerServe.getMyd()),"满意度不能为空");
        customerServe.setState(CustomerStateEnum.ARCHIVE.getCode());
        AssertUtil.isTrue(customerServeMapper.updateByPrimaryKeySelective(customerServe) <1,"服务反馈失败");
    }

    /**
     * 进行服务处理操作 处理完成之后设置状态为待反馈 以备下一步操作
     * @param customerServe
     */
    private void updateCustomerServerForProce(CustomerServe customerServe) {
        checkCustomerServeInfoParam(customerServe);
        AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProce()),"服务处理内容不能为空");
        customerServe.setServiceProceTime(new Date());
        //customerServe.setState(CustomerStateEnum.FEEDBACK.getCode());
        AssertUtil.isTrue(customerServeMapper.updateByPrimaryKeySelective(customerServe) < 1,"服务处理失败");
    }

    /**
     * 查询服务数据的聚合统计结果
     * @param customerServeQuery
     * @return
     */
    public Map<String, Object> queryCustomerServeInfo(CustomerServeQuery customerServeQuery) {
        HashMap<String, Object> resultMap = new HashMap<>();
        List<Map<String,Object>> listMap = customerServeMapper.queryCustomerServeInfo(customerServeQuery);
        PageHelper.startPage(customerServeQuery.getPage(),customerServeQuery.getLimit());
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(listMap);
        resultMap.put("msg","");
        resultMap.put("code",0);
        resultMap.put("data",pageInfo.getList());
        resultMap.put("count",pageInfo.getTotal());
        return resultMap;
    }

    /**
     * 查询以用户名为维度的服务统计数据
     * @return
     */
    public Map<String, Object> customerServeInfo() {
        List<Map<String,Object>> listMap = customerServeMapper.customerServeInfo();
        HashMap<String, Object> resultMap = new HashMap<>();
        ArrayList<String> customerNames = new ArrayList<>();
        ArrayList<Long> nums = new ArrayList<>();
        for (Map<String, Object> map : listMap) {
            customerNames.add((String) map.get("customer"));
            nums.add((Long) map.get("count"));
        }
        resultMap.put("data1",customerNames);
        resultMap.put("data2",nums);
        return resultMap;
    }
}
