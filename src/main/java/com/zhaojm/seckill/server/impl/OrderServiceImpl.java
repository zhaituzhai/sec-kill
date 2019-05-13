package com.zhaojm.seckill.server.impl;

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
        //校验库存
        StockDTO stock = checkStock(sid);

        //扣库存
        saleStock(stock);

        //创建订单
        int id = createOrder(stock);

        return id;
    }
    
    private StockDTO checkStock(int sid) {
        StockDTO stock = stockMapper.selectByPrimaryKey(sid);
        if (stock.getSale().equals(stock.getCount())) {
            throw new RuntimeException("库存不足");
        }
        return stock;
    }

    private int saleStock(StockDTO stock) {
        stock.setSale(stock.getSale() + 1);
        return stockMapper.updateByPrimaryKeySelective(stock);
    }

    private int createOrder(StockDTO stock) {
        StockOrderDTO order = new StockOrderDTO();
        order.setSid(stock.getId());
        order.setName(stock.getName());
        int id = stockOrderMapper.insertSelective(order);
        return id;
    } 
    
    /**
     * 2.乐观锁生成订单
     */
    @Override
    public int createOptimisticOrder(int sid) {
        //校验库存
        StockDTO stock = checkStock(sid);

        //乐观锁更新库存
        saleStockOptimistic(stock);

        //创建订单
        int id = createOrder(stock);

        return id;
    }
    
    /**
     * 乐观锁扣
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
        int id = createOrder(stock);
        return id ;
    }
    
    private StockDTO checkStockByRedis(int sid) {
        StockDTO stock = (StockDTO) redisTemplate.opsForValue().get(RedisKeysConstant.STOCK_GOODS + sid);
        if(null == stock) {
            StockDTO stockInit = stockMapper.selectByPrimaryKey(sid);
            redisTemplate.opsForValue().set(RedisKeysConstant.STOCK_GOODS + sid, stockInit);
            stock = stockInit;
        }
//        Integer count = Integer.parseInt();
//        Integer sale = Integer.parseInt(redisTemplate.opsForValue().get(RedisKeysConstant.STOCK_SALE + sid));
        if (stock.getCount().equals(stock.getSale())){
            throw new RuntimeException("库存不足 Redis currentCount=" + stock.getSale());
        }
//        Integer version = Integer.parseInt(redisTemplate.opsForValue().get(RedisKeysConstant.STOCK_VERSION + sid));
//        StockDTO stock = new StockDTO() ;
//        stock.setId(sid);
//        stock.setCount(count);
//        stock.setSale(sale);
//        stock.setVersion(version);

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
//        redisTemplate.opsForValue().increment(RedisKeysConstant.STOCK_VERSION + stock.getId(), 1) ;
    }

}
