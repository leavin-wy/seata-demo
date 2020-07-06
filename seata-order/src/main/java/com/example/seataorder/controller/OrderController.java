package com.example.seataorder.controller;

import com.example.seataorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @订单
 * @Autor leavin
 * @Date 2020/7/2
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
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/createOrderByTcc")
    public String createOrderByTcc(){
        try {
            this.orderService.createOrderByTcc();
            return "succeed";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/updateOrder")
    public void updateOrder(@RequestParam(value = "orderId") String orderId){
        this.orderService.updateOrder(orderId);
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
