package com.example.seataaccount.controller;

import com.example.seataaccount.model.Account;
import com.example.seataaccount.service.AccountService;
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
