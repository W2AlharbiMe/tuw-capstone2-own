package com.example.capstone2.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateInventoryDTO {

    @NotNull(message = "the max capacity field is required.")
    @Positive(message = "the max capacity must be positive number.")
    private Integer maxCapacity; // how many cars can you store in this inventory ?

    @NotEmpty(message = "the country field is required.")
    private String country;

    @NotEmpty(message = "the city field is required.")
    private String city;

    @NotEmpty(message = "the address field is required.")
    private String address;
}
