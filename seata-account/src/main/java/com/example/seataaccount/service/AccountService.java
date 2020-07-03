package com.example.seataaccount.service;

import com.example.seataaccount.model.Account;

import java.math.BigDecimal;

/**
 * @Title
 * @Autor leavin
 * @Date 2020/7/2
 */
public interface AccountService {
    void deductionAccount(Account account,Long orderId);
}
