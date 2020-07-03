package com.example.seataorder.feign;

import com.example.seataorder.model.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @Title
 * @Autor leavin
 * @Date 2020/7/2
 */
@FeignClient(value = "account-service")
public interface AccountClient {

    //@Hmily
    @GetMapping("/account")
    public String deductionAccount(Map<String, Object> param);
}
