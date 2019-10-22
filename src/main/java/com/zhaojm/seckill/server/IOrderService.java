package com.zhaojm.seckill.server;

import com.zhaojm.seckill.dto.UpdateStockDTO;

public interface IOrderService {

    /**
     * 创建订单：多线程中操作时订单数多于商品剩余量
     * @param sid 商品id
     * @return
     */
    int createWrongOrder(int sid);

    /**
     * 创建订单乐观锁：乐观锁更新商品剩余量，多线程中订单量等于商品剩余量
     * @param sid 商品id
     * @return 
     */
    int createOptimisticOrder(int sid);

    /**
     * 创建订单乐观锁加Redis
     * @param sid 商品id
     * @return 是否创建成功
     */
    int createOptimisticOrderUseRedis(int sid);

    /**
     * 更新商品的库存
     * @param sid 商品id
     * @param amount 需要增加的数量
     * @return 是否更新成功
     */
    int updateStore(UpdateStockDTO updateDTO);

    /**
     * 创建订单乐观锁加Redis
     * @param sid 商品id
     * @return 是否创建成功
     */
    int createRedisLockOrder(int sid);

}
