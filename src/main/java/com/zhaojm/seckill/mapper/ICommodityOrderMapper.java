package com.zhaojm.seckill.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.zhaojm.seckill.dto.CommodityOrderDTO;

@Mapper
public interface ICommodityOrderMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(CommodityOrderDTO record);

    int insertSelective(CommodityOrderDTO record);

    CommodityOrderDTO selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(CommodityOrderDTO record);

    int updateByPrimaryKey(CommodityOrderDTO record);
}