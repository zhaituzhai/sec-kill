package com.zhaojm.seckill.common;

/**
 * Redis (remote dictionary service) Keys constant
 * @author 赵嘉敏
 * 
 */
public interface RedisKeysConstant {

    /**
     * 查询库存
     */
    String STOCK_COUNT = "stock_count_";

    /**
     * 已售数量
     */
    String STOCK_SALE = "stock_sale_";

    /**
     * 版本
     */
    String STOCK_VERSION = "stock_version_";
    
    /**
     * stock goods 商品
     */
    String STOCK_GOODS = "stock_goods_";
    
    String GOODS_STOCK = "goods_stock_";
}
