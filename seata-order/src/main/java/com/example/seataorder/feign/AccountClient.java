package com.example.seataorder.feign;

import com.example.seataorder.model.Account;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @Title
 * @Autor zxf
 * @Date 2019/9/7
 */
@FeignClient(value = "account-service")
public interface AccountClient {

    @Hmily
    @GetMapping("/account")
    public String deductionAccount(Account account);
}
