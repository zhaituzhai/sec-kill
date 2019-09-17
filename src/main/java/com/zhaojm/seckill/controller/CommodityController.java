package com.zhaojm.seckill.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhaojm.seckill.server.ICommodityService;

@RestController
@RequestMapping("/buy")
public class CommodityController {
    private static Logger logger = LoggerFactory.getLogger(CommodityController.class);

    @Autowired
    ICommodityService commodityService;

    @GetMapping("/createNomalOrder")
    public int createNomalOrder(@RequestParam Integer cid) {
        logger.info("商品id[{}]", cid);
        if (null == cid) {
            return 0;
        }
        return commodityService.createNomalOrder(cid);
    }

}
