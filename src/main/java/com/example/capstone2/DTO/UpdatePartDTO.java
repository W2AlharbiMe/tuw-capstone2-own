package com.example.capstone2.DTO;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder(toBuilder = true) // this is used to take default initialized values into account.
public class UpdatePartDTO {
    @NotEmpty(message = "the name field is required.")
    private String name;

    private String description = "";

    @NotNull(message = "the purchase price field is required.")
    private Double purchasePrice;

    private Boolean isUsed = false;
}
