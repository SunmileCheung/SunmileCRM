package com.sunmile.crm.dao;

import com.sunmile.crm.base.BaseMapper;
import com.sunmile.crm.query.SaleChanceQuery;
import com.sunmile.crm.vo.SaleChance;

import java.util.List;


public interface SaleChanceMapper extends BaseMapper<SaleChance,Integer> {

    public List<SaleChance> querySaleChanceTableByCondition(SaleChanceQuery saleChanceQuery);

}