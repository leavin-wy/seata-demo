package com.example.seatainventory.service;

import com.example.seatainventory.model.Inventory;
import com.example.seatainventory.mapper.InventoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Title
 * @Autor leavin
 * @Date 2020/7/2
 */
@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    //@Hmily(confirmMethod = "confirm", cancelMethod = "cancel")
    public void removeInventory(Long id, Integer productNumber){
        Inventory record = this.inventoryMapper.selectByPrimaryKey(id);
        if(record.getProductInventory()<productNumber){
            throw new RuntimeException("库存不足");
        }
        Inventory inventory = new Inventory();
        inventory.setId(id);
        inventory.setProductInventory(record.getProductInventory()-productNumber);
        this.inventoryMapper.updateByPrimaryKeySelective(inventory);
    }

    public void confirm(){
        System.out.println("执行确认方法");
    }

    public void cancel(){
        System.out.println("执行取消方法");
    }

    @Override
    public void addInventory(Long id, Integer productNumber){
        Inventory inventory = new Inventory();
        inventory.setId(id);
        inventory.setProductInventory(productNumber);
        inventory.setCreateTime(new Date());
        inventory.setUpdateTime(new Date());
        this.inventoryMapper.insert(inventory);
    }
}
