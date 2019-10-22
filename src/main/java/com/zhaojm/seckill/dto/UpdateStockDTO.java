package com.zhaojm.seckill.dto;

import java.io.Serializable;

public class UpdateStockDTO implements Serializable {
    private static final long serialVersionUID = -7238032256191678046L;
    private int sid;
    private int amount;
    public int getSid() {
        return sid;
    }
    public void setSid(int sid) {
        this.sid = sid;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
