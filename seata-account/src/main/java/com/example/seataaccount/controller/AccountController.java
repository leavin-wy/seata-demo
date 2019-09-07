package com.example.seataaccount.controller;

import com.example.seataaccount.model.Account;
import com.example.seataaccount.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @Title
 * @Autor zxf
 * @Date 2019/9/7
 */
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/account")
    public String deductionAccount(@RequestBody Account account){
        try {
            this.accountService.deductionAccount(account);
            return "succeed";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
