package com.example.capstone2.Repository;

import com.example.capstone2.Model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Integer> {
    InventoryItem findInventoryItemById(Integer id);

    List<InventoryItem> findInventoryItemsByInventoryId(Integer inventoryId);

    InventoryItem findByItemId(Integer id);


    InventoryItem findByItemIdAndType(Integer id, String type);

    @Query("SELECT i FROM inventory_items i WHERE i.inventoryId = ?1 ORDER BY i.id ASC LIMIT 1")
    InventoryItem atLeastOneItem(Integer inventoryId);


    @Query("SELECT SUM(i.quantity) FROM inventory_items i WHERE i.inventoryId = ?1")
    Integer totalItemsInInventory(Integer inventoryId);
}
