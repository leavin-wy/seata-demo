package com.example.seataaccount.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Title
 * @Autor leavin
 * @Date 2020/7/2
 */
@FeignClient(value = "order-service")
public interface OrderClient {

    //@GetMapping("/updateOrder")
    @RequestMapping(value = "/updateOrder", method = RequestMethod.GET)
    public void updateOrder(@RequestParam(value = "orderId") String orderId);
}
