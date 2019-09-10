package com.zhaojm.seckill.server.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhaojm.seckill.dto.CommodityDTO;
import com.zhaojm.seckill.dto.CommodityOrderDTO;
import com.zhaojm.seckill.mapper.ICommodityMapper;
import com.zhaojm.seckill.mapper.ICommodityOrderMapper;
import com.zhaojm.seckill.server.ICommodityService;

@Service
public class CommodityServiceImpl implements ICommodityService {

    @Autowired
    ICommodityMapper commodityMapper;

    @Autowired
    ICommodityOrderMapper commodityOrderMapper;

    /**
     * 正常创建订单逻辑
     * 添加一条时，可以正常正确的添加订单，
     * 如果高并发（100次，0秒）的情况下则查询商品100次，订单记录100，更新库存则制作了6次。
     */
    @Override
    @Transactional
    public int createNomalOrder(Integer cid) {
	// 1、检查商品库存；
	CommodityDTO commodity = this.checkCommodityStock(cid);
	// 2、减库存；
	this.saleCommodity(commodity);
	// 3、记录订单；
	return this.createOrder(commodity);
    }

    /**
     * 创建订单
     * 
     * @param commodity 商品
     * @return 新增条数
     */
    private Integer createOrder(CommodityDTO commodity) {
	CommodityOrderDTO orderDTO = new CommodityOrderDTO();
	orderDTO.setCommodityId(commodity.getCommodityId());
	orderDTO.setCreateTime(new Date());
	orderDTO.setCommodityCount(1);
	return commodityOrderMapper.insertSelective(orderDTO);
    }

    /**
     * 减库存
     * 
     * @param commodity 商品
     * @return 更改数据
     */
    private Integer saleCommodity(CommodityDTO commodity) {
	commodity.setCommoditySale(commodity.getCommoditySale() + 1);
	return commodityMapper.updateByPrimaryKeySelective(commodity);
    }

    /**
     * 检查商品库存
     * 
     * @param cid 商品id
     * @return 商品
     */
    private CommodityDTO checkCommodityStock(Integer cid) {
	CommodityDTO commodity = commodityMapper.selectByPrimaryKey(cid);
	if (null == commodity) {
	    throw new RuntimeException("未查询到数据");
	}
	if (commodity.getCommodityStock() - commodity.getCommoditySale() <= 0) {
	    throw new RuntimeException("物品无库存！");
	}
	return commodity;
    }

}
