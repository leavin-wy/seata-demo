package com.example.seataorder.model;

import java.math.BigDecimal;
import java.util.Date;

public class Account {
    private Long id;

    private BigDecimal balance;

    private Long userId;

    private Date createIme;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateIme() {
        return createIme;
    }

    public void setCreateIme(Date createIme) {
        this.createIme = createIme;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}