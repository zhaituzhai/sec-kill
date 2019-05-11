package com.zhaojm.seckill;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zhaojm.seckill.dto.StockDTO;
import com.zhaojm.seckill.mapper.IStockMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MysqlConnectionTest {

    @Autowired
    private IStockMapper stockMapper;
    
    @Test
    public void testSelectStock() {
        StockDTO stock = stockMapper.selectByPrimaryKey(1);
        System.out.println(stock.getName());
    }
    
}
