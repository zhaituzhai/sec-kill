package com.zhaojm.seckill.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class StockOrderDTO implements Serializable {
    private Integer id;

    //("库存ID")
    private Integer sid;

    //("商品名称")
    private String name;

    //("创建时间")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}