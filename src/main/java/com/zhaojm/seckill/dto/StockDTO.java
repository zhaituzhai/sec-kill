package com.zhaojm.seckill.dto;

import java.io.Serializable;

/**
 * @author 
 */
public class StockDTO implements Serializable {
    private Integer id;

    //("名称")
    private String name;

    //("库存")
    private Integer count;

    //("已售")
    private Integer sale;

    //("乐观锁，版本号")
    private Integer version;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}