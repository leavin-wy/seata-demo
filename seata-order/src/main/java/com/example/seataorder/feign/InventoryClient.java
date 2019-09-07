package com.example.seataorder.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Title
 * @Autor zxf
 * @Date 2019/9/7
 */
@FeignClient(value = "inventory-service")
public interface InventoryClient {

    @GetMapping("/inventory")
    public String removeInventory(@RequestParam(value = "id") Long id,
                                  @RequestParam(value = "productNumber")Integer productNumber);
}
