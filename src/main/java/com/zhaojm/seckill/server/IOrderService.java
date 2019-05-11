package com.zhaojm.seckill.server;

public interface IOrderService {

    public int createWrongOrder(int sid);
    
    public int createOptimisticOrder(int sid);
    
    public int createOptimisticOrderUseRedis(int sid);
    
}
