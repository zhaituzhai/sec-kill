package com.zhaojm.seckill.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.zhaojm.seckill.dto.StockDTO;

@Mapper
public interface IStockMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StockDTO record);

    int insertSelective(StockDTO record);

    StockDTO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StockDTO record);

    int updateByPrimaryKey(StockDTO record);

    int updateStockByOptimistic(StockDTO stock);
}