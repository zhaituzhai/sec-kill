package com.zhaojm.seckill.server;

public interface IOrderService {

    /**
     * 创建订单：多线程中操作时订单数多于商品剩余量
     * @param sid
     * @return
     */
    public int createWrongOrder(int sid);

    /**
     * 创建订单乐观锁：乐观锁更新商品剩余量，多线程中订单量等于商品剩余量
     * @param sid
     * @return
     */
    public int createOptimisticOrder(int sid);

    /**
     * 创建订单乐观锁加Redis
     * @param sid
     * @return
     */
    public int createOptimisticOrderUseRedis(int sid);

}
