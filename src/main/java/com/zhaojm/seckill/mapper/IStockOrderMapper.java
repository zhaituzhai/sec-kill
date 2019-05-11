package com.zhaojm.seckill.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.zhaojm.seckill.dto.StockOrderDTO;

@Mapper
public interface IStockOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StockOrderDTO record);

    int insertSelective(StockOrderDTO record);

    StockOrderDTO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StockOrderDTO record);

    int updateByPrimaryKey(StockOrderDTO record);
}