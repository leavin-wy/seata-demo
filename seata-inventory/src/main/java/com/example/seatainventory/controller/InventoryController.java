package com.example.seatainventory.controller;

import com.example.seatainventory.service.InventoryService;
import org.omg.CORBA.portable.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;

/**
 * @Title
 * @Autor leavin
 * @Date 2020/7/2
 */
@RestController
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/del_inventory")
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

    @GetMapping("/add_inventory")
    public String addInventory(@RequestParam(value = "id") Long id,
                                  @RequestParam(value = "productNumber")Integer productNumber){
        this.inventoryService.addInventory(id,productNumber);
        return "success";
    }
}
