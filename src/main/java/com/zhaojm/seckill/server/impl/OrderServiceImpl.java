package com.zhaojm.seckill.server.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.zhaojm.seckill.common.RedisKeysConstant;
import com.zhaojm.seckill.dto.StockDTO;
import com.zhaojm.seckill.dto.StockOrderDTO;
import com.zhaojm.seckill.mapper.IStockMapper;
import com.zhaojm.seckill.mapper.IStockOrderMapper;
import com.zhaojm.seckill.server.IOrderService;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IStockMapper stockMapper;
    
    @Autowired
    private IStockOrderMapper stockOrderMapper;
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate ;
    
    /**
     * 1.多线程错误生成错误数量订单
     */
    @Override
    public int createWrongOrder(int sid) {
        // 1、校验库存
        StockDTO stock = checkStock(sid);
        // 2、扣库存
        saleStock(stock);
        // 3、创建订单
        return createOrder(stock);
    }
    
    /**
     * 查询库存
     * @param sid
     * @return StockDTO
     */
    private StockDTO checkStock(int sid) {
        StockDTO stock = stockMapper.selectByPrimaryKey(sid);
        if (stock.getSale().equals(stock.getCount())) {
            throw new RuntimeException("库存不足");
        }
        return stock;
    }

    /**
     * 修改库存信息
     * @param stock
     * @return int
     */
    private int saleStock(StockDTO stock) {
        stock.setSale(stock.getSale() + 1);
        return stockMapper.updateByPrimaryKeySelective(stock);
    }

    /**
     * 创建订单
     * @param stock
     * @return int
     */
    private int createOrder(StockDTO stock) {
        StockOrderDTO order = new StockOrderDTO();
        order.setSid(stock.getId());
        order.setName(stock.getName());
        int id = stockOrderMapper.insertSelective(order);
        return id;
    } 
    
    @Override
    public int createOptimisticOrder(int sid) {
        //校验库存
        StockDTO stock = checkStock(sid);
        //乐观锁更新库存
        saleStockOptimistic(stock);
        //创建订单
        return createOrder(stock);
    }
    
    /**
     * 乐观锁更新库存
     * @param stock
     */
    private void saleStockOptimistic(StockDTO stock) {
        int count = stockMapper.updateStockByOptimistic(stock);
        if (count == 0){
            throw new RuntimeException("并发更新库存失败") ;
        }
    }
    
    /**
     * 3、乐观锁 + Redis
     */
    @Override
    public int createOptimisticOrderUseRedis(int sid) {
        //检验库存，从 Redis 获取
        StockDTO stock = checkStockByRedis(sid);
        //乐观锁更新库存 以及更新 Redis
        saleStockOptimisticByRedis(stock);
        //创建订单
        return createOrder(stock) ;
    }
    
    private StockDTO checkStockByRedis(int sid) {
        // 1、从Redis中读取库存信息
        StockDTO stock = (StockDTO) redisTemplate.opsForValue().get(RedisKeysConstant.STOCK_GOODS + sid);
        if(null == stock) {
            // 2.1、如果redis中没有库存信息则查找数据库中，并储存于redis中，设置redis中过期时间
            StockDTO stockInit = stockMapper.selectByPrimaryKey(sid);
            redisTemplate.opsForValue().set(RedisKeysConstant.STOCK_GOODS + sid, stockInit);
            redisTemplate.expire(RedisKeysConstant.STOCK_GOODS + sid, 60, TimeUnit.MINUTES);
            stock = stockInit;
        }
        if (stock.getCount().equals(stock.getSale())){
            throw new RuntimeException("库存不足 Redis currentCount=" + stock.getSale());
        }
        return stock;
    }
    
    /**
     * 乐观锁更新数据库 还要更新 Redis
     * @param stock
     */
    private void saleStockOptimisticByRedis(StockDTO stock) {
        int count = stockMapper.updateStockByOptimistic(stock);
        if (count == 0){
            throw new RuntimeException("并发更新库存失败") ;
        }
        //自增
        stock.setSale(stock.getSale() + 1);
        stock.setVersion(stock.getVersion() + 1);
        redisTemplate.opsForValue().getAndSet(RedisKeysConstant.STOCK_GOODS + stock.getId(), stock) ;
    }

}
