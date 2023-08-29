package com.example.capstone2.Service;

import com.example.capstone2.Api.Exception.ResourceNotFoundException;
import com.example.capstone2.Api.Exception.SimpleException;
import com.example.capstone2.DTO.UpdateInventoryItemDTO;
import com.example.capstone2.Model.*;
import com.example.capstone2.Repository.CarRepository;
import com.example.capstone2.Repository.InventoryItemRepository;
import com.example.capstone2.Repository.InventoryRepository;
import com.example.capstone2.Repository.SalesInvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InventoryItemService {

    private final InventoryRepository inventoryRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final CarRepository carRepository;
    private final SalesInvoiceRepository salesInvoiceRepository;
    private final PartService partService;




    public List<InventoryItem> findAllItemsInInventory(Integer inventoryId) throws ResourceNotFoundException {
        List<InventoryItem> inventoryItems = inventoryItemRepository.findInventoryItemsByInventoryId(inventoryId);

        if(inventoryItems.isEmpty()) {
            throw new ResourceNotFoundException("inventory");
        }

        return inventoryItems;
    }


    public InventoryItem findById(Integer id) {
        InventoryItem inventoryItem = inventoryItemRepository.findInventoryItemById(id);

        if(inventoryItem == null) {
            throw new ResourceNotFoundException("inventory item");
        }

        return inventoryItem;
    }

    public InventoryItem findByItemId(Integer ItemId) {
        InventoryItem inventoryItem = inventoryItemRepository.findByItemId(ItemId);

        if(inventoryItem == null) {
            throw new ResourceNotFoundException("inventory item");
        }

        return inventoryItem;
    }

    public InventoryItem findByItemIdAndType(Integer ItemId, String type) {
        if(!(type.equalsIgnoreCase("car") || type.equalsIgnoreCase("part"))) {
            throw new SimpleException("invalid type.");
        }

        InventoryItem inventoryItem = inventoryItemRepository.findByItemIdAndType(ItemId, type);

        if(inventoryItem == null) {
            throw new ResourceNotFoundException("inventory item");
        }

        return inventoryItem;
    }


    public HashMap<String, Object> addInventoryItem(InventoryItem inventoryItem) throws ResourceNotFoundException, SimpleException {
        // this will validate:
        // 1. inventory exists
        // 2. inventory is full
        // 3. make sure not to allow user to insert a quantity that's greater than inventory max capacity
        // 4. validate car id provided from item id
        // 5. make sure that the provided car isn't already stored.
        validate(inventoryItem);


        inventoryItem.setType(inventoryItem.getType().toLowerCase());

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the inventory item have been added.");
        response.put("inventoryItem", inventoryItemRepository.save(inventoryItem));

        return response;
    }

    public HashMap<String, Object> updateInventoryItem(Integer id, UpdateInventoryItemDTO updateInventoryItemDTO) throws ResourceNotFoundException, SimpleException {
        InventoryItem inventoryItem = inventoryItemRepository.findInventoryItemById(id);

        if(inventoryItem == null) {
            throw new ResourceNotFoundException("inventory item");
        }

        // this will validate:
        // 1. inventory exists
        // 2. inventory is full
        // 3. make sure not to allow user to insert a quantity that's greater than inventory max capacity
        // 4. validate car id provided from item id
        // 5. make sure that the provided car isn't already stored.
        validate(updateInventoryItemDTO);

        inventoryItem.setInventoryId(updateInventoryItemDTO.getInventoryId());
        inventoryItem.setItemId(updateInventoryItemDTO.getItemId());
        inventoryItem.setQuantity(updateInventoryItemDTO.getQuantity());
        inventoryItem.setType(updateInventoryItemDTO.getType());

        inventoryItemRepository.save(inventoryItem);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the inventory item have been updated.");
        response.put("inventoryItem", inventoryItem);

        return response;

    }

    public HashMap<String, Object> deleteInventoryItem(Integer id) {
        InventoryItem inventoryItem = inventoryItemRepository.findInventoryItemById(id);

        if(inventoryItem == null) {
            throw new ResourceNotFoundException("inventory item");
        }

        inventoryItemRepository.deleteById(id);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the inventory item have been deleted.");
        response.put("inventoryItem", inventoryItem);

        return response;
    }


    private void validate(InventoryItem inventoryItem) throws ResourceNotFoundException, SimpleException {
        Inventory inventory = inventoryRepository.findInventoryById(inventoryItem.getInventoryId());

        if(inventory == null) {
            throw new ResourceNotFoundException("inventory");
        }

        Integer totalStoredItems = inventoryItemRepository.totalItemsInInventory(inventory.getId());

        if(totalStoredItems == null) {
            totalStoredItems = 0;
        }

        if(Objects.equals(totalStoredItems, inventory.getMaxCapacity()) || totalStoredItems >= inventory.getMaxCapacity()) {
            throw new SimpleException("this inventory is full.");
        }

        // check capacity
        if(inventoryItem.getQuantity() > inventory.getMaxCapacity()) {
            throw new SimpleException("you are trying to store (" + inventoryItem.getQuantity() + ") in inventory with max capacity of ("+inventory.getMaxCapacity()+").");
        }

        String message = "this car is already stored.";

        if(inventoryItem.getType().equalsIgnoreCase("car")) {
            Car car = carRepository.findCarById(inventoryItem.getItemId());

            if(car == null) {
                throw new ResourceNotFoundException("car");
            }
        }

        if(inventoryItem.getType().equalsIgnoreCase("part")) {
            Part part = partService.findById(inventoryItem.getItemId());
            message = "this part is already stored.";
        }

        // make sure each car|part only have 1 inventory item
        if(inventoryItemRepository.findByItemIdAndType(inventoryItem.getItemId(), inventoryItem.getType()) != null) {
            throw new SimpleException(message);
        }
    }


    private void validate(UpdateInventoryItemDTO updateInventoryItemDTO) throws ResourceNotFoundException, SimpleException {
        Inventory inventory = inventoryRepository.findInventoryById(updateInventoryItemDTO.getInventoryId());

        if(inventory == null) {
            throw new ResourceNotFoundException("inventory");
        }

        Integer totalStoredItems = inventoryItemRepository.totalItemsInInventory(inventory.getId());

        if(Objects.equals(totalStoredItems, inventory.getMaxCapacity()) || totalStoredItems >= inventory.getMaxCapacity()) {
            throw new SimpleException("this inventory is full.");
        }

        // check capacity
        if(updateInventoryItemDTO.getQuantity() > inventory.getMaxCapacity()) {
            throw new SimpleException("you are trying to store (" + updateInventoryItemDTO.getQuantity() + ") in inventory with max capacity of ("+inventory.getMaxCapacity()+").");
        }

        String message = "this car is already stored.";

        if(updateInventoryItemDTO.getType().equalsIgnoreCase("car")) {
            Car car = carRepository.findCarById(updateInventoryItemDTO.getItemId());

            if(car == null) {
                throw new ResourceNotFoundException("car");
            }
        }

        if(updateInventoryItemDTO.getType().equalsIgnoreCase("part")) {
            partService.findById(updateInventoryItemDTO.getItemId());

            message = "this part is already stored.";
        }

        if(inventoryItemRepository.findByItemIdAndType(updateInventoryItemDTO.getItemId(), updateInventoryItemDTO.getType()) != null) {
            throw new SimpleException(message);
        }
    }
}
