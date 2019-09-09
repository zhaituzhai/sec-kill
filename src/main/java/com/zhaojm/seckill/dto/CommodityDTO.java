package com.zhaojm.seckill.dto;

import java.io.Serializable;

/**
 * @author 
 */
public class CommodityDTO implements Serializable {
    private Integer commodityId;

    //("商品名称")
    private String commodityName;

    //("商品描述")
    private String commodityDesc;

    private Long commodityStock;

    private Long commoditySale;

    private static final long serialVersionUID = 1L;

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityDesc() {
        return commodityDesc;
    }

    public void setCommodityDesc(String commodityDesc) {
        this.commodityDesc = commodityDesc;
    }

    public Long getCommodityStock() {
        return commodityStock;
    }

    public void setCommodityStock(Long commodityStock) {
        this.commodityStock = commodityStock;
    }

    public Long getCommoditySale() {
        return commoditySale;
    }

    public void setCommoditySale(Long commoditySale) {
        this.commoditySale = commoditySale;
    }
}