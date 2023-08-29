package com.example.capstone2.Controller;

import com.example.capstone2.DTO.UpdateInventoryItemDTO;
import com.example.capstone2.Model.InventoryItem;
import com.example.capstone2.Service.InventoryItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory-items")
@RequiredArgsConstructor
public class InventoryItemsController {

    private final InventoryItemService inventoryItemService;

    @GetMapping("/get/{inventoryId}")
    private ResponseEntity<List<InventoryItem>> findAllByInventoryId(@PathVariable Integer inventoryId) {
        return ResponseEntity.ok(inventoryItemService.findAllItemsInInventory(inventoryId));
    }

    @GetMapping("/search/id/{id}")
    private ResponseEntity<InventoryItem> findOneById(@PathVariable Integer id) {
        return ResponseEntity.ok(inventoryItemService.findById(id));
    }

    @GetMapping("/search/item-id/{itemId}")
    private ResponseEntity<List<InventoryItem>> findOneByItemId(@PathVariable Integer itemId) {
        return ResponseEntity.ok(inventoryItemService.findByItemId(itemId));
    }

    @GetMapping("/search/{type}/{itemId}")
    private ResponseEntity<List<InventoryItem>> findOneByItemId(@PathVariable String type, @PathVariable Integer itemId) {
        return ResponseEntity.ok(inventoryItemService.findByItemIdAndType(type));
    }

    @PostMapping("/add")
    private ResponseEntity<HashMap<String, Object>> addItem(@RequestBody @Valid UpdateInventoryItemDTO updateInventoryItemDTO) {
        return ResponseEntity.ok(inventoryItemService.addInventoryItem(updateInventoryItemDTO));
    }

    @PutMapping("/update/{id}")
    private ResponseEntity<HashMap<String, Object>> updateItem(@PathVariable Integer id, @RequestBody @Valid UpdateInventoryItemDTO updateInventoryItemDTO) {
        return ResponseEntity.ok(inventoryItemService.updateInventoryItem(id, updateInventoryItemDTO));
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<HashMap<String, Object>> deleteItem(@PathVariable Integer id) {
        return ResponseEntity.ok(inventoryItemService.deleteInventoryItem(id));
    }

}
