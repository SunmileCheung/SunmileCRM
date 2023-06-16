package com.sunmile.crm.dao;

import com.sunmile.crm.base.BaseMapper;
import com.sunmile.crm.query.CusDevPlanQuery;
import com.sunmile.crm.vo.CusDevPlan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CusDevPlanMapper extends BaseMapper<CusDevPlan,Integer> {

    List<CusDevPlan> querySaleChanceTableByCondition(CusDevPlanQuery cusDevPlanQuery);
}