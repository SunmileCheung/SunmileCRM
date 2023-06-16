package com.sunmile.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sunmile.crm.base.BaseService;
import com.sunmile.crm.base.ResultInfo;
import com.sunmile.crm.dao.SaleChanceMapper;
import com.sunmile.crm.enums.DevResult;
import com.sunmile.crm.enums.StateStatus;
import com.sunmile.crm.query.CusDevPlanQuery;
import com.sunmile.crm.query.SaleChanceQuery;
import com.sunmile.crm.util.AssertUtil;
import com.sunmile.crm.util.PhoneUtil;
import com.sunmile.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/4 11:36
 * @Description
 * @Modified by Sunmile
 */
@Service
public class SaleChanceService extends BaseService<SaleChance,Integer> {

    @Resource
    private SaleChanceMapper saleChanceMapper;



    /**
     * 通过传入的saleChanceQuery对象进行数据表格数据的查询和展示
     * @param saleChanceQuery saleChance查询对象
     * @return 返回结果集
     */
    public Map<String,Object> querySaleChanceTableByCondition(SaleChanceQuery saleChanceQuery){
        HashMap<String, Object> resultMap = new HashMap<>();
        AssertUtil.isTrue(Objects.isNull(saleChanceQuery),"传入的saleChanceQuery对象为空");
        List<SaleChance> saleChances = saleChanceMapper.querySaleChanceTableByCondition(saleChanceQuery);
        PageHelper.startPage(saleChanceQuery.getPage(),saleChanceQuery.getLimit());
        PageInfo<SaleChance> pageInfo = new PageInfo<>(saleChances);
        resultMap.put("count",pageInfo.getTotal());
        resultMap.put("code",0);
        resultMap.put("msg","success");
        resultMap.put("data",pageInfo.getList());
        return resultMap;
    }


    /**
     *  1.参数校验
     *       customerName  客户名非空
     *       linkMan  非空
     *       linkPhone  非空 11位手机号
     *  2. 设置相关参数默认值
     *        state 默认未分配   如果选择分配人  state 为已分配状态
     *        assignTime 默认空   如果选择分配人  分配时间为系统当前时间
     *        devResult  默认未开发  如果选择分配人 devResult 为开发中 0-未开发  1-开发中 2-开发成功 3-开发失败
     *        isValid  默认有效(1-有效  0-无效)
     *        createDate  updateDate:默认系统当前时间
     *  3.执行添加 判断添加结果
     * @param saleChance
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(SaleChance saleChance) {
        //校验必要参数
        checkSaleChanceParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        //进行相关数据的赋值操作
        optimizeTheInsertDto(saleChance);
        AssertUtil.isTrue(saleChanceMapper.insertSelective(saleChance) < 1,"修改失败");
    }

    /**
     * 判断分配人信息，并设置对应的字段值
     * @param saleChance 要进行参数写入的dto
     */
    private void optimizeTheInsertDto(SaleChance saleChance) {
        //isValid  默认有效(1-有效  0-无效)
        saleChance.setIsValid(1);
        // createDate  updateDate:默认系统当前时间
        saleChance.setCreateDate(new Date());
        saleChance.setUpdateDate(new Date());
        //如果分配人不为空
        if (StringUtils.isNotBlank(saleChance.getAssignMan())){
            //state 默认未分配   如果选择分配人  state 为已分配状态
            saleChance.setState(StateStatus.STATED.getType());
            //assignTime 默认空   如果选择分配人  分配时间为系统当前时间
            saleChance.setAssignTime(new Date());
            //devResult  默认未开发  如果选择分配人 devResult 为开发中 0-未开发  1-开发中 2-开发成功 3-开发失败
            saleChance.setDevResult(DevResult.DEVING.getStatus());

        }else {
            //如果分配人为空
            //state 默认未分配   如果选择分配人  state 为已分配状态
            saleChance.setState(StateStatus.UNSTATED.getType());
            saleChance.setDevResult(DevResult.UNDEV.getStatus());
        }
    }

    /**
     * 校验必要参数是否合法
     * @param customerName 客户姓名
     * @param linkMan 联系人
     * @param linkPhone 联系电话
     */
    private void checkSaleChanceParams(String customerName, String linkMan, String linkPhone) {
        AssertUtil.isTrue(StringUtils.isBlank(customerName),"客户名称不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan),"联系人不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone),"联系电话不能为空");
        AssertUtil.isTrue(!PhoneUtil.isMobile(linkPhone),"联系人电话格式不正确");
    }


    /**
     *1.参数校验
     *     id 记录必须存在
     *     customerName  客户名非空
     *     linkMan  非空
     *     linkPhone  非空 11位手机号
     *     -----------------------------------------------------------------------------------------------------------------
     * 2.设置相关参数值
     *     updateDate  系统当前时间
     *       原始记录 未分配 修改后 已分配(分配人是否存在)
     *          state   0--->1
     *          assignTime   设置分配时间 系统时间
     *          devResult  0--->1
     *       原始记录  已分配  修改后  未分配
     *         state 1-->0
     *         assignTime  null
     *         devResult 1-->0
     *     -----------------------------------------------------------------------------------------------------------------
     *  3.执行更新 判断结果
     *
     * 依据前端弹出层传入的salechance对象，进行数据的编辑与更新操作
     * 在更新之前首先进行数据的验证和编辑
     * @param saleChance
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(SaleChance saleChance) {
        AssertUtil.isTrue(Objects.isNull(saleChance.getId()),"要进行更新的数据不存在");
        SaleChance oldSaleChance = saleChanceMapper.selectByPrimaryKey(saleChance.getId());
        AssertUtil.isTrue(Objects.isNull(oldSaleChance),"要进行更新的数据不存在");
        //对客户名称，联系人，联系方式进行必要的校验
        checkSaleChanceParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        //设置相关参数 以备更新
        setTheUpdateOperationDto(saleChance,oldSaleChance);
        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance) != 1, "数据更新失败");
    }

    /**
     * 执行更新操作时需要对数据进行校验和编辑
     *
     * @param newSaleChance    要进行更新操作的数据dto
     * @param oldSaleChance 原始的数据dto
     */
    private void setTheUpdateOperationDto(SaleChance newSaleChance, SaleChance oldSaleChance) {
        /**
         *  updateDate  系统当前时间
         *     原始记录 未分配 修改后 已分配(分配人是否存在)
         *        state   0--->1
         *        assignTime   设置分配时间 系统时间
         *        devResult  0--->1
         *     原始记录  已分配  修改后  未分配
         *       state 1-->0
         *       assignTime  null
         *       devResult 1-->0
         */
        //updateDate  系统当前时间
        newSaleChance.setUpdateDate(new Date());
        //原始记录，未分配
        if (StringUtils.isBlank(oldSaleChance.getAssignMan())){
            //修改后 已分配(分配人是否存在)
            if (StringUtils.isNotBlank(newSaleChance.getAssignMan())){
                /**
                 * state   0--->1
                 *         assignTime   设置分配时间 系统时间
                 *         devResult  0--->1
                 */
                newSaleChance.setState(StateStatus.STATED.getType());
                newSaleChance.setAssignTime(new Date());
                newSaleChance.setDevResult(DevResult.DEVING.getStatus());
            }
        }else {
            /**
             * 原始记录  已分配  修改后  未分配
             *   state 1-->0
             *   assignTime  null
             *   devResult 1-->0
             */
            if (StringUtils.isBlank(newSaleChance.getAssignMan())){
                newSaleChance.setState(StateStatus.UNSTATED.getType());
                newSaleChance.setAssignTime(null);
                newSaleChance.setDevResult(DevResult.UNDEV.getStatus());
            }else {
                //重新分配了分配人，更新分配时间
                newSaleChance.setAssignTime(new Date());
            }
        }

    }
    /**
     * 批量删除
     * @param ids
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ResultInfo deleteBatch(Integer[] ids) throws DataAccessException {
        ResultInfo<Object> resultInfo = new ResultInfo<>();
        AssertUtil.isTrue(null == ids || ids.length < 1,"请选择您要删除的选项");
        AssertUtil.isTrue(saleChanceMapper.deleteBatch(ids) < ids.length, "删除操作失败");
        resultInfo.success("删除成功",null);
        return resultInfo;
    }


    /**
     * 依据前端传入的销售机会id和要修改的devResult开发状态进行销售机会数据的修改操作
     * @param id 销售机会id
     * @param devResult 销售机会数据对应的开发状态
     * @return 返回json格式的修改结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSaleChanceDevResult(Integer id, Integer devResult) {
        AssertUtil.isTrue(Objects.isNull(id),"销售机会id不能为空");
        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(Objects.isNull(saleChance),"该销售机会id对应的销售机会数据为空");
        AssertUtil.isTrue(Objects.isNull(devResult),"要进行修改的开发状态不能为空");
        saleChance.setDevResult(devResult);
        saleChance.setUpdateDate(new Date());
        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance) != 1,"数据修改失败");
    }
}
