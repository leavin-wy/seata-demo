package com.example.seataorder.service;

import com.example.seataorder.feign.AccountClient;
import com.example.seataorder.feign.InventoryClient;
import com.example.seataorder.mapper.OrderMapper;
import com.example.seataorder.model.Account;
import com.example.seataorder.model.Order;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @Title
 * @Autor zxf
 * @Date 2019/9/7
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private InventoryClient inventoryClient;

    @Autowired
    private AccountClient accountClient;

    @Override
    @GlobalTransactional
    public void createOrder() {
        Order order = this.setOrder();
        test(order);
    }

    @Override
    @GlobalTransactional
    public void createOrderFail() {
        Order order = this.setOrder();
        order.setUserId(2L);
        test(order);
    }

    private Order setOrder(){
        Order order = new Order();
        Random random = new Random();
        int id = random.nextInt(100000000);
        order.setId((long)id);
        order.setProductId(2L);
        order.setProductPrice(new BigDecimal(1));
        order.setProductNumber(1);
        order.setUserId(1L);
        return order;
    }

    private void test(Order order) {
        this.orderMapper.insertSelective(order);
        this.inventoryClient.removeInventory(order.getProductId(), order.getProductNumber());
        Account account = new Account();
        account.setUserId(order.getUserId());
        account.setBalance(order.getProductPrice());
        this.accountClient.deductionAccount(account);
    }
}
