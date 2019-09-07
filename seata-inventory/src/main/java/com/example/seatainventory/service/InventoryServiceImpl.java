package com.example.seatainventory.service;

import com.example.seatainventory.model.Inventory;
import com.example.seatainventory.mapper.InventoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Title
 * @Autor zxf
 * @Date 2019/9/7
 */
@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
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
}
