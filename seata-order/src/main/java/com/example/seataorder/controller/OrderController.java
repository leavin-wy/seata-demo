package com.example.seataorder.controller;

import com.example.seataorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @订单
 * @Autor zxf
 * @Date 2019/9/7
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order")
    public String createOrder(){
        try {
            this.orderService.createOrder();
            return "succeed";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/order/fail")
    public String createOrderFail(){
        try {
            this.orderService.createOrderFail();
            return "succeed";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
