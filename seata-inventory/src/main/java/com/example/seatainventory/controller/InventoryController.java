package com.example.seatainventory.controller;

import com.example.seatainventory.service.InventoryService;
import org.omg.CORBA.portable.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;

/**
 * @Title
 * @Autor zxf
 * @Date 2019/9/7
 */
@RestController
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/inventory")
    public String removeInventory(@RequestParam(value = "id") Long id,
                                  @RequestParam(value = "productNumber")Integer productNumber){
        try {
            this.inventoryService.removeInventory(id,productNumber);
            return "succeed";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
