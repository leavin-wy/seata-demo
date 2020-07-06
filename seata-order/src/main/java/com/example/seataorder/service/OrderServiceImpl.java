package com.example.seataorder.service;

import com.example.seataorder.feign.AccountClient;
import com.example.seataorder.feign.InventoryClient;
import com.example.seataorder.mapper.OrderMapper;
import com.example.seataorder.model.Account;
import com.example.seataorder.model.Order;
import io.seata.common.XID;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Title
 * @Autor leavin
 * @Date 2020/7/2
 */
@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

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
        //新增订单
        this.orderMapper.insertSelective(order);
        //减库存
        this.inventoryClient.removeInventory(order.getProductId(), order.getProductNumber());
        Map<String, Object> param = new HashMap<>();
        param.put("userId",order.getUserId());
        param.put("balance",order.getProductPrice());
        param.put("orderId",order.getId());
        /*Account account = new Account();
        account.setUserId(order.getUserId());
        account.setBalance(order.getProductPrice());*/
        //扣账户金额
        this.accountClient.deductionAccount(param);
    }

    @Override
    @GlobalTransactional//开启全局事务（重点） 使用 seata 的全局事务
    public void createOrderByTcc() {
        Order order = this.setOrder();
        //新增订单
        this.orderMapper.insertSelective(order);
        //int i = 1/0;
        //账户扣钱参与者
        Map<String, Object> firstParam = new HashMap<>();
        firstParam.put("userId",order.getUserId());
        firstParam.put("balance",order.getProductPrice());
        firstParam.put("orderId",order.getId());
        //扣账户金额
        boolean first = this.accountClient.firstTccAccount(firstParam);
        //扣钱参与者，一阶段失败; 回滚本地事务和分布式事务
        if(!first){
            throw new RuntimeException("===========扣钱处理失败===========");
        }
        //账户收钱参与者
        Map<String, Object> secondParam = new HashMap<>();
        secondParam.put("userId",2L);
        secondParam.put("balance",order.getProductPrice());
        secondParam.put("orderId",order.getId());
        boolean second = this.accountClient.secondTccAccount(secondParam);
        //收钱参与者，一阶段失败; 回滚本地事务和分布式事务
        if(!second){
            throw new RuntimeException("===========收钱处理失败===========");
        }
    }

    @Override
    //@Hmily(confirmMethod = "confirm", cancelMethod = "cancel")
    public void createOrderFail() {
        /*Order order = this.setOrder();
        this.orderMapper.insertSelective(order);
        this.inventoryClient.removeInventory(order.getProductId(), order.getProductNumber());
        Account account = new Account();
        account.setUserId(order.getUserId());
        account.setBalance(order.getProductPrice());
        this.accountClient.deductionAccount(account);*/
    }

    @Override
    public void updateOrder(String orderId){
        String xid = RootContext.getXID();
        logger.info("=========更新订单信息开始，xid={}=========", xid);
        Order order = new Order();
        order.setId(Long.valueOf(orderId));
        order.setUpdateTime(new Date());
        this.orderMapper.updateByPrimaryKeySelective(order);
        logger.info("=========更新订单信息结束，xid={}=========", xid);
    }

    public void confirm(){
        System.out.println("执行确认方法");
    }

    public void cancel(){
        System.out.println("执行取消方法");
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
        order.setCreateTime(new Date());
        return order;
    }
}
