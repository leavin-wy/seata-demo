package com.example.seatainventory.service;

/**
 * @Title
 * @Autor leavin
 * @Date 2020/7/2
 */
public interface InventoryService {
    void removeInventory(Long id, Integer productNumber);

    void addInventory(Long id, Integer productNumber);
}
