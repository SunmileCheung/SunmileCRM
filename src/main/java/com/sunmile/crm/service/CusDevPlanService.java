package com.sunmile.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sunmile.crm.base.BaseService;
import com.sunmile.crm.base.ResultInfo;
import com.sunmile.crm.dao.CusDevPlanMapper;
import com.sunmile.crm.query.CusDevPlanQuery;
import com.sunmile.crm.util.AssertUtil;
import com.sunmile.crm.vo.CusDevPlan;
import com.sunmile.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/5 16:21
 * @Description
 * @Modified by Sunmile
 */
@Service
public class CusDevPlanService extends BaseService<CusDevPlan,Integer> {

    @Resource
    private CusDevPlanMapper cusDevPlanMapper;

    /**
     * 通过saleChanceId查询客户开发计划
     * @param cusDevPlanQuery 查询封装类
     * @return 返回分页结果及数据
     */
    public Map<String, Object> queryCusDevPlanBySaleChanceId(CusDevPlanQuery cusDevPlanQuery) {

        HashMap<String, Object> resultMap = new HashMap<>();
        AssertUtil.isTrue(Objects.isNull(cusDevPlanQuery),"传入的cusDevPlanQuery对象为空");
        List<CusDevPlan> cusDevPlans = cusDevPlanMapper.querySaleChanceTableByCondition(cusDevPlanQuery);
        PageHelper.startPage(cusDevPlanQuery.getPage(),cusDevPlanQuery.getLimit());
        PageInfo<CusDevPlan> pageInfo = new PageInfo<>(cusDevPlans);
        resultMap.put("count",pageInfo.getTotal());
        resultMap.put("code",0);
        resultMap.put("msg","success");
        resultMap.put("data",pageInfo.getList());
        return resultMap;
    }

    /**
     *  1.参数校验
     *     机会id 非空 记录必须存在
     *     计划项内容非空
     *     计划项时间非空
     *  2. 参数默认值
     *     is_valid  1
     *     createDate 系统时间
     *     updateDate  系统时间
     *  3.执行添加 判断结果
     * @param cusDevPlan
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveCusDevPlan(CusDevPlan cusDevPlan) {
        checkFormTableParams(cusDevPlan);
        cusDevPlan.setIsValid(1);
        cusDevPlan.setCreateDate(new Date());
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(cusDevPlanMapper.insertSelective(cusDevPlan) < 1,"数据插入失败");

    }

    /**
     * 在执行添加操作前，校验从前端传入的form表单的数据
     * 1.参数校验
     *    机会id 非空 记录必须存在
     *    计划项内容非空
     *    计划项时间非空
     * 2. 参数默认值
     *    is_valid  1
     *    createDate 系统时间
     *    updateDate  系统时间
     * 3.执行添加 判断结果
     * @param cusDevPlan 从前端传入的客户开发计划的数据
     */
    private void checkFormTableParams(CusDevPlan cusDevPlan) {
        //机会id 非空 记录必须存在
        AssertUtil.isTrue(Objects.isNull(cusDevPlan.getSaleChanceId()),"销售机会id不能为空");
        //计划项内容非空
        AssertUtil.isTrue(StringUtils.isBlank(cusDevPlan.getPlanItem()),"计划内容不能为空");
        //计划项时间非空
        AssertUtil.isTrue(null == cusDevPlan.getPlanDate(),"计划时间不能为空");
    }

    /**
     * 当点击每行记录后的tool按钮 编辑操作时，进行数据的更新操作
     * @param cusDevPlan 前端传过来的要更新的数据
     */
    public void updateCusDevPlan(CusDevPlan cusDevPlan) {
        AssertUtil.isTrue(Objects.isNull(cusDevPlan.getId()),"客户开发计划id不能为空");
        checkFormTableParams(cusDevPlan);
        AssertUtil.isTrue(Objects.isNull(cusDevPlanMapper.selectByPrimaryKey(cusDevPlan.getId())),"该id对应的客户开发计划不存在");
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(cusDevPlanMapper.updateByPrimaryKeySelective(cusDevPlan) < 1,"客户开发计划数据更新失败");
    }

    /**
     * 点击删除按钮，依据传入的id进行相应的客户开发计划的逻辑删除操作
     * @param id 客户开发计划对应id
     * @return 返回ResultInfo
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteCusdevPlan(Integer id) {
        AssertUtil.isTrue(Objects.isNull(id),"传入的用户开发计划id不能为空");
        CusDevPlan cusDevPlan = cusDevPlanMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(Objects.isNull(cusDevPlan),"该id对应的用户开发计划不存在");
        cusDevPlan.setUpdateDate(new Date());
        cusDevPlan.setIsValid(0);
        AssertUtil.isTrue(cusDevPlanMapper.updateByPrimaryKeySelective(cusDevPlan) != 1,"用户开发计划更新失败");
    }
}
