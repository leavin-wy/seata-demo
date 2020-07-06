package com.example.seataaccount.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

import java.math.BigDecimal;

@LocalTCC   //适用于SpringCloud+Feign模式下的TCC
public interface SecondTccAccountService {

    @TwoPhaseBusinessAction(name = "SecondTccAccountService",commitMethod = "commit",rollbackMethod = "rollback")
    boolean prepareMinus(BusinessActionContext businessActionContext,
                         @BusinessActionContextParameter(paramName = "userId") Long userId,
                         @BusinessActionContextParameter(paramName = "amount") BigDecimal amount);

    boolean commit(BusinessActionContext businessActionContext);

    boolean rollback(BusinessActionContext businessActionContext);
}
