package com.zhaojm.seckill.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crossoverjie.distributed.annotation.SpringControllerLimit;
import com.zhaojm.seckill.dto.UpdateStockDTO;
import com.zhaojm.seckill.server.IOrderService;

@RestController
@RequestMapping
public class SecKillController {

    public static Logger logger = LoggerFactory.getLogger(SecKillController.class);

    @Autowired
    private IOrderService orderService;
    
    @PostMapping("updataStore")
    public int updateStore(@RequestBody UpdateStockDTO updateDTO) {
        logger.info("sid=[{}],amount=[{}]", updateDTO.getSid(), updateDTO.getAmount());
        return orderService.updateStore(updateDTO);
    }

    /**
     * 错误秒杀商品： 库存10件，使用100线程同时请求接口时订单生成65
     * 
     * @param sid
     * @return
     */
    @GetMapping("createWrongOrder/{sid}")
    public String createWrongOrder(@PathVariable int sid) {
        logger.info("sid=[{}]", sid);
        int id = 0;
        try {
            id = orderService.createWrongOrder(sid);
        } catch (Exception e) {
            logger.error("Exception", e);
        }
        return String.valueOf(id);
    }

    /**
     * 乐观锁秒杀商品
     * 
     * @param sid
     * @return
     */
    @GetMapping("/createOptimisticOrder/{sid}")
    public String createOptimisticOrder(@PathVariable int sid) {
        logger.info("sid=[{}]", sid);
        int id = 0;
        try {
            id = orderService.createOptimisticOrder(sid);
        } catch (Exception e) {
            logger.error("Exception", e);
        }
        return String.valueOf(id);
    }
    
    @GetMapping("")
    public String createRedisLockOrder(@PathVariable int sid) {
        logger.info("sid=[{}]", sid);
        int id = 0;
        try {
            id = orderService.createRedisLockOrder(sid);
        } catch (Exception e) {
            logger.error("Exception", e);
        }
        return String.valueOf(id);
    }

    /**
     * 乐观锁+分布式限流
     * 
     * @param sid
     * @return
     */
    @SpringControllerLimit(errorCode = 200)
    @GetMapping("/createOptimisticLimitOrder/{sid}")
    public String createOptimisticLimitOrder(@PathVariable int sid) {
        logger.info("sid=[{}]", sid);
        int id = 0;
        try {
            id = orderService.createOptimisticOrder(sid);
        } catch (Exception e) {
            logger.error("Exception", e);
        }
        return String.valueOf(id);
    }

    /**
     * 乐观锁+分布式限流+reids
     * 
     * @param sid
     * @return
     */
    @SpringControllerLimit(errorCode = 200, errorMsg = "request has limited")
    @GetMapping("/createOptimisticLimitOrderByRedis/{sid}")
    public String createOptimisticLimitOrderByRedis(@PathVariable int sid) {
        logger.info("sid=[{}]", sid);
        int id = 0;
        try {
            id = orderService.createOptimisticOrderUseRedis(sid);
        } catch (Exception e) {
            logger.error("Exception", e);
        }
        return String.valueOf(id);
    }

}
