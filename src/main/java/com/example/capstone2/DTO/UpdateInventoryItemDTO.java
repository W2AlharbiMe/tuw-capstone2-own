package com.example.capstone2.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateInventoryItemDTO {

    @NotNull(message = "the item id field is required.")
    @Positive(message = "the item id must be positive number.")
    @Column(unique = true)
    private Integer itemId; // you can store cars, or parts.


    @NotNull(message = "the inventory id field is required.")
    @Positive(message = "the inventory id must be positive number.")
    @Column(nullable = false)
    private Integer inventoryId;


    @NotEmpty(message = "the type field is required.")
    @Pattern(message = "the type can only be `car` or `part`", regexp = "(?i)\\b(car|part)\\b?")
    @Column(nullable = false)
    private String type; // car or part ?


    @NotNull(message = "the quantity field is required.")
    @Positive(message = "the quantity must be positive number.")
    @Min(value = 5, message = "the quantity must be at least 5.")
    @Column(nullable = false)
    private Integer quantity; // you can not store more than < maxCapacity

}
