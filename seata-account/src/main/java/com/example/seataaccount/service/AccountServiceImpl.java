package com.example.seataaccount.service;

import com.example.seataaccount.mapper.AccountMapper;
import com.example.seataaccount.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Title
 * @Autor zxf
 * @Date 2019/9/7
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public void deductionAccount(Account account) {
        Account record = this.accountMapper.selectByPrimaryKey(account.getUserId());
        BigDecimal subtract = record.getBalance().subtract(account.getBalance());
        if(subtract.intValue()<0){
            throw new RuntimeException("资金不足");
        }
        account.setBalance(subtract);
        this.accountMapper.updateByPrimaryKeySelective(account);
    }
}
