package com.example.seataaccount.service;

import com.example.seataaccount.mapper.AccountMapper;
import com.example.seataaccount.model.Account;
import io.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Title
 * @Autor leavin
 * @Date 2020/7/2
 */
@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountMapper accountMapper;

    @Override
    //@Hmily(confirmMethod = "confirm", cancelMethod = "cancel")
    public void deductionAccount(Account account) {
        Object xid = RootContext.getXID();
        logger.info("=========扣减账户开始，xid={}========",xid);
        Account record = this.accountMapper.selectByPrimaryKey(account.getUserId());
        BigDecimal subtract = record.getBalance().subtract(account.getBalance());
        if(subtract.intValue()<0){
            throw new RuntimeException("资金不足");
        }
        //account.setId(account.getUserId());
        account.setBalance(subtract);
        //account.setCreateTime(new Date());
        account.setUpdateTime(new Date());
        this.accountMapper.updateByPrimaryKeySelective(account);
        //int i = 1/0;
        logger.info("=========扣减账户结束，xid={}========",xid);
    }

    public void confirm(){
        System.out.println("执行确认方法");
    }

    public void cancel(){
        System.out.println("执行取消方法");
    }

}
