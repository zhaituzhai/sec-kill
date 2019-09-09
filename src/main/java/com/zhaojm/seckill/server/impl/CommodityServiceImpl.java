package com.zhaojm.seckill.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhaojm.seckill.dto.CommodityDTO;
import com.zhaojm.seckill.mapper.ICommodityMapper;
import com.zhaojm.seckill.mapper.ICommodityOrderMapper;
import com.zhaojm.seckill.server.ICommodityService;

@Service
public class CommodityServiceImpl implements ICommodityService {

    @Autowired
    ICommodityMapper commodityMapper;
    
    @Autowired
    ICommodityOrderMapper commodityOrderMapper;
    
    @Override
    public int createNomalOrder(Integer cid) {
	// 1、检查商品库存；
	CommodityDTO commodity = this.checkCommodityStock(cid);
	// 2、减库存；
	// 3、记录订单；
	return 0;
    }

    private CommodityDTO checkCommodityStock(Integer cid) {
	CommodityDTO commodity = commodityMapper.selectByPrimaryKey(cid);
	if(null == commodity) {
	    throw new RuntimeException("未查询到数据");
	}
	if(commodity.getCommodityStock() - commodity.getCommoditySale() <= 0) {
	    throw new RuntimeException("物品无库存！");
	}
	return commodity;
    }

}
