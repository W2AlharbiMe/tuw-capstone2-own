package com.example.capstone2.Repository;

import com.example.capstone2.Model.Car;
import com.example.capstone2.Model.InventoryItem;
import com.example.capstone2.Model.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Integer> {
    InventoryItem findInventoryItemById(Integer id);

    List<InventoryItem> findInventoryItemsByInventoryId(Integer inventoryId);
//
//    InventoryItem findByItemId(Integer id);
//
//
//    InventoryItem findByItemIdAndType(Integer id, String type);

    List<InventoryItem> findInventoryItemsByType(String type);

    List<InventoryItem> findInventoryItemsByCarId(Integer id);

    List<InventoryItem> findInventoryItemsByPartId(Integer id);


    @Query("SELECT i FROM inventory_items i WHERE i.inventory.id = ?1 ORDER BY i.id ASC LIMIT 1")
    InventoryItem atLeastOneItem(Integer inventoryId);


    @Query("SELECT SUM(i.quantity) FROM inventory_items i WHERE i.inventory.id = ?1")
    Integer totalItemsInInventory(Integer inventoryId);
}
