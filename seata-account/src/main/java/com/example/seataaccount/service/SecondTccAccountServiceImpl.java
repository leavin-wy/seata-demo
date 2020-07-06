package com.example.seataaccount.service;

import com.example.seataaccount.mapper.AccountMapper;
import com.example.seataaccount.model.Account;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;

/**
 * 收钱账户处理
 * @author leavin
 */
@Service
public class SecondTccAccountServiceImpl implements SecondTccAccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecondTccAccountServiceImpl.class);

    @Autowired
    private AccountMapper accountMapper;

    /**
     * 收钱数据源事务模板
     */
    @Autowired
    private TransactionTemplate fromDsTransactionTemplate;
    /**
     *  一阶段准备，转入资金准备
     * @param userId
     * @param amount
     * @return
     */
    @Override
    public boolean prepareMinus(BusinessActionContext businessActionContext,final Long userId,final BigDecimal amount) {
        LOGGER.info("================收款一阶段准备=================");
        //分布式事务ID
        final String xid = RootContext.getXID();
        //final String xid = businessActionContext.getXid();

        return fromDsTransactionTemplate.execute(new TransactionCallback<Boolean>(){
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                try {
                    //校验账户余额
                    Account account = accountMapper.selectByPrimaryKey(userId);
                    if(account == null){
                        throw new RuntimeException("账户不存在");
                    }
                    //冻结不可用金额
                    BigDecimal freezedAmount = account.getFreeBalance().add(amount);
                    account.setFreeBalance(freezedAmount);
                    accountMapper.updateByPrimaryKeySelective(account);
                    LOGGER.info("prepareMinus account：{}， amount：{}, transaction id: {}.",userId,amount,xid);
                    //int i = 1/0;
                    return true;
                } catch (Throwable t) {
                    t.printStackTrace();
                    status.setRollbackOnly();
                    return false;
                }
            }
        });
    }

    /**
     * 二阶段提交
     * @param businessActionContext
     * @return
     */
    @Override
    public boolean commit(BusinessActionContext businessActionContext) {
        LOGGER.info("================收款二阶段提交=================");
        //分布式事务ID
        //final String xid = businessActionContext.getXid();
        final String xid = RootContext.getXID();
        //账户ID
        final Long userId = Long.valueOf(String.valueOf(businessActionContext.getActionContext("userId")));
        //转出金额
        final BigDecimal amount = new BigDecimal(String.valueOf(businessActionContext.getActionContext("amount")));
        return fromDsTransactionTemplate.execute(new TransactionCallback<Boolean>() {

            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                try{
                    Account account = accountMapper.selectByPrimaryKey(userId);
                    //增加账户余额
                    BigDecimal newAmount = account.getBalance().add(amount);
                    account.setBalance(newAmount);
                    //释放账户 冻结金额
                    account.setFreeBalance(account.getFreeBalance().subtract(amount));
                    accountMapper.updateByPrimaryKeySelective(account);
                    LOGGER.info("minus account：{}， amount：{}, transaction id: {}.",userId,amount,xid);
                    return true;
                }catch (Throwable t){
                    t.printStackTrace();
                    status.setRollbackOnly();
                    return false;
                }
            }
        });
    }

    /**
     * 二阶段回滚
     * @param businessActionContext
     * @return
     */
    @Override
    public boolean rollback(BusinessActionContext businessActionContext) {
        LOGGER.info("================收款二阶段回滚=================");
        //分布式事务ID
        final String xid = RootContext.getXID();
        //final String xid = businessActionContext.getXid();
        //账户ID
        final Long userId = Long.valueOf(String.valueOf(businessActionContext.getActionContext("userId")));
        //转出金额
        final BigDecimal amount = new BigDecimal(String.valueOf(businessActionContext.getActionContext("amount")));
        return fromDsTransactionTemplate.execute(new TransactionCallback<Boolean>() {

            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                try{
                    Account account = accountMapper.selectByPrimaryKey(userId);
                    if(account == null){
                        //账户不存在，回滚什么都不做
                        return true;
                    }
                    //释放冻结金额
                    account.setFreeBalance(account.getFreeBalance().subtract(amount));
                    accountMapper.updateByPrimaryKeySelective(account);
                    LOGGER.info("Undo prepareMinus account: {}, amount: {}, dtx transaction id: {}.",userId,amount,xid);
                    return true;
                }catch (Throwable t){
                    t.printStackTrace();
                    status.setRollbackOnly();
                    return false;
                }
            }
        });
    }
}
