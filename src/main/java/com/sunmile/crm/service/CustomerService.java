package com.sunmile.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sunmile.crm.base.BaseService;
import com.sunmile.crm.dao.CustomerLossMapper;
import com.sunmile.crm.dao.CustomerMapper;
import com.sunmile.crm.dao.CustomerOrderMapper;
import com.sunmile.crm.query.CustomerQuery;
import com.sunmile.crm.util.AssertUtil;
import com.sunmile.crm.util.PhoneUtil;
import com.sunmile.crm.vo.Customer;
import com.sunmile.crm.vo.CustomerLinkMan;
import com.sunmile.crm.vo.CustomerLoss;
import com.sunmile.crm.vo.CustomerOrder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/8 10:34
 * @Description  用户信息service层
 * @Modified by Sunmile
 */
@Service
public class CustomerService extends BaseService<Customer,Integer> {
    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private CustomerLossMapper customerLossMapper;

    @Resource
    private CustomerOrderMapper customerOrderMapper;

    /**
     * 保存用户信息
     *
     *
     *  1.参数校验
     *      客户名称  name 非空 不可重复
     *      phone 联系电话  非空 格式合法
     *      法人  fr 非空
     *  2.参数默认值
     *      isValid
     *      createDate
     *      updateDate
     *      state  流失状态  0-未流失 1-已流失
     * 3.执行添加 判断结果
     *
     * @param customer 从前端传回的用户信息
     * @return 返回保存结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveCustomer(Customer customer) {
        //1.参数校验
        checkParamOfUpdateOrSaveOperation(customer.getName(),customer.getPhone(),customer.getFr());
        Customer temp1 = customerMapper.queryCustomerInfoByCustomerName(customer.getName());
        AssertUtil.isTrue(Objects.nonNull(temp1),"该客户名已存在，请检查输入信息");
        //2.参数默认值
        customer.setIsValid(1);
        customer.setCreateDate(new Date());
        customer.setUpdateDate(new Date());
        customer.setState(0);
        //对客户编号进行赋值操作
        customer.setKhno("KH"+System.currentTimeMillis());
        //3.执行添加 判断结果
        AssertUtil.isTrue(customerMapper.insertSelective(customer) < 1,"客户信息添加失败");
    }

    /**
     * 1.参数校验
     *     客户名称  name 非空 不可重复
     *     phone 联系电话  非空 格式合法
     *     法人  fr 非空
     * @param name
     * @param phone
     * @param fr
     */
    private void checkParamOfUpdateOrSaveOperation(String name, String phone, String fr) {
        AssertUtil.isTrue(StringUtils.isBlank(name),"客户名称不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(phone),"客户联系电话不能为空");
        AssertUtil.isTrue(!PhoneUtil.isMobile(phone),"联系电话格式不合法");
        AssertUtil.isTrue(StringUtils.isBlank(fr),"法人信息不能为空");
    }

    /**
     *  1.参数校验
     *      id 存在性校验
     *      客户名称  name 非空 不可重复
     *      phone 联系电话  非空 格式合法
     *      法人  fr 非空
     *  2.参数默认值
     *      updateDate
     * 3.执行更新 判断结果
     * @param customer
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCustomer(Customer customer){
        //1.参数校验
        AssertUtil.isTrue(Objects.isNull(customer.getId()),"客户id不能为空");
        Customer temp1 = customerMapper.queryCustomerInfoByCustomerName(customer.getName());
        AssertUtil.isTrue(!customer.getId().equals(temp1.getId()),"该客户名已存在，请检查输入");
        checkParamOfUpdateOrSaveOperation(customer.getName(),customer.getPhone(),customer.getFr());
        //2.参数默认值
        customer.setUpdateDate(new Date());
        //3.执行更新 判断结果
        AssertUtil.isTrue(customerMapper.updateByPrimaryKeySelective(customer) < 1,"客户信息修改失败");

    }

    /**
     * 订单详情页展示
     * @param orderId 通过订单id查询 订单详情
     * @return
     */
    public Map<String, Object> queryOrderDetailPageByOrderId(Integer orderId) {
        AssertUtil.isTrue(Objects.isNull(orderId),"订单id不能为空");
        System.out.println("测试前"+orderId);
        Map<String,Object> resultMap = customerMapper.queryOrderDetailPageByOrderId(orderId).get(0);
        System.out.println("测试后");
        return resultMap;

    }

    /**
     * 逻辑删除客户信息
     * @param cusId 客户id
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void logicDeleteCustomByCusId(Integer cusId) {
        AssertUtil.isTrue(Objects.isNull(cusId),"客户id不能为空");
        Customer customer = customerMapper.selectByPrimaryKey(cusId);
        AssertUtil.isTrue(Objects.isNull(customer),"该客户id对应的记录不存在");
        customer.setIsValid(0);
        AssertUtil.isTrue((customerMapper.updateByPrimaryKeySelective(customer) < 1),"客户信息记录删除失败");
    }

    /**
     * 流失客户管理
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCustomerState(){
        List<Customer> lossCutomers = customerMapper.queryLossCustomers();
        if(null !=lossCutomers && lossCutomers.size()>0){
            List<CustomerLoss> customerLosses= new
                    ArrayList<CustomerLoss>();
            List<Integer> lossCusIds=new ArrayList<Integer>();
            lossCutomers.forEach(customer->{
                CustomerLoss customerLoss=new CustomerLoss();
                // 设置最后下单时间
                CustomerOrder lastCustomerOrder = customerOrderMapper.queryLastCustomerOrderByCusId(customer.getId())
                        ;
                if(null !=lastCustomerOrder){
                    customerLoss.setLastOrderTime(lastCustomerOrder.getOrderDate());
                }
                customerLoss.setCreateDate(new Date());
                customerLoss.setCusManager(customer.getCusManager());
                customerLoss.setCusName(customer.getName());
                customerLoss.setCusNo(customer.getKhno());
                customerLoss.setIsValid(1);
                // 设置客户流失状态为暂缓流失状态
                customerLoss.setState(0);
                customerLoss.setUpdateDate(new Date());
                customerLosses.add(customerLoss);
                lossCusIds.add(customer.getId());
            });
            AssertUtil.isTrue(customerLossMapper.insertBatch(customerLosses)
                    <customerLosses.size(),"客户流失数据流转失败!");
            AssertUtil.isTrue(customerMapper.updateCustomerStateByIds(lossCusIds)<lossCusIds.size(),"客户流失数据流转失败!");
        }
    }


    public Map<String, Object> queryCustomerContributionByParams(CustomerQuery customerQuery) {
        Map<String,Object> result = new HashMap<String,Object>();
        PageHelper.startPage(customerQuery.getPage(),customerQuery.getLimit());
        List<Map<String,Object>>  list = customerMapper.queryCustomerContributionByParams(customerQuery);
        PageInfo<Map<String,Object>> pageInfo =new PageInfo<Map<String,Object>>(list);
        result.put("count",pageInfo.getTotal());
        result.put("data",pageInfo.getList());
        result.put("code",0);
        result.put("msg","");
        return result;


    }

    /**
     * 客户构成分析
     * @return 返回客户构成数据
     */
    public Map<String, Object> countCustoerMake() {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> maps = customerMapper.countCustoerMake();
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<Long> list2 = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            list1.add((String) map.get("level"));
            list2.add((Long) map.get("count"));
        }
        resultMap.put("data1",list1);
        resultMap.put("data2",list2);
        return resultMap;
    }

    /**
     * 查询饼状图需要的数据
     * @return
     */
    public Map<String, Object> countCutomerMake02() {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> maps = customerMapper.countCustoerMake();
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<Map<String,Object>> list2 = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            list1.add((String) map.get("level"));
            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("name",map.get("level"));
            map1.put("value",map.get("count"));
            list2.add(map1);
        }
        resultMap.put("data1",list1);
        resultMap.put("data2",list2);
        return resultMap;
    }

}
