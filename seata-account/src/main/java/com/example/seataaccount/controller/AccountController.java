package com.example.seataaccount.controller;

import com.example.seataaccount.model.Account;
import com.example.seataaccount.service.AccountService;
import com.example.seataaccount.service.FirstTccAccountService;
import com.example.seataaccount.service.SecondTccAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @Title
 * @Autor leavin
 * @Date 2020/7/2
 */
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private FirstTccAccountService firstTccAccountService;
    @Autowired
    private SecondTccAccountService secondTccAccountService;

    @PostMapping("/account")
    public String deductionAccount(@RequestBody Map<String, Object> param){
        try {
            Account account = new Account();
            account.setUserId(((Integer) param.get("userId")).longValue());
            account.setBalance(new BigDecimal((Integer)param.get("balance")));
            Long orderId = ((Integer) param.get("orderId")).longValue();
            this.accountService.deductionAccount(account,orderId);
            return "succeed";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 扣钱账户处理
     * @param param
     * @return
     */
    @PostMapping("/firstTccAccount")
    public boolean firstTccAccount(@RequestBody Map<String, Object> param){
    //public boolean firstTccAccount(Long userId, BigDecimal amount){
        try {
            Long userId = ((Integer) param.get("userId")).longValue();
            BigDecimal amount = new BigDecimal((Integer)param.get("balance"));
            return firstTccAccountService.prepareMinus(null,userId, amount);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 收钱账户处理
     * @param param
     * @return
     */
    @PostMapping("/secondTccAccount")
    public boolean secondTccAccount(@RequestBody Map<String, Object> param){
        //public boolean firstTccAccount(Long userId, BigDecimal amount){
        try {
            Long userId = ((Integer) param.get("userId")).longValue();
            BigDecimal amount = new BigDecimal((Integer)param.get("balance"));
            return secondTccAccountService.prepareMinus(null,userId, amount);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/deAccount")
    public String deAccount(@RequestParam(value = "balance") BigDecimal balance,
                            @RequestParam(value = "userId")Long userId,
                            @RequestParam(value = "orderId")Long orderId){
        try {
            Account account = new Account();
            account.setBalance(balance);
            account.setUserId(userId);
            this.accountService.deductionAccount(account,orderId);
            return "succeed";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
