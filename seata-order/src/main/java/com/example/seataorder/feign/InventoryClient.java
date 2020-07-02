package com.example.seataorder.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Title
 * @Autor leavin
 * @Date 2020/7/2
 */
@FeignClient(value = "inventory-service")
public interface InventoryClient {

    //@Hmily
    @GetMapping("/del_inventory")
    public String removeInventory(@RequestParam(value = "id") Long id,
                                  @RequestParam(value = "productNumber")Integer productNumber);
}
