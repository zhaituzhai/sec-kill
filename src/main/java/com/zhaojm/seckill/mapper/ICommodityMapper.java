package com.zhaojm.seckill.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.zhaojm.seckill.dto.CommodityDTO;

@Mapper
public interface ICommodityMapper {
    int deleteByPrimaryKey(Integer commodityId);

    int insert(CommodityDTO record);

    int insertSelective(CommodityDTO record);

    CommodityDTO selectByPrimaryKey(Integer commodityId);

    int updateByPrimaryKeySelective(CommodityDTO record);

    int updateByPrimaryKey(CommodityDTO record);
}