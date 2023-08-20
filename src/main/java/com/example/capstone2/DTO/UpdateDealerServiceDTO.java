package com.example.capstone2.DTO;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class UpdateDealerServiceDTO {
    @NotEmpty(message = "the name field is required.")
    private String name;

    @NotNull(message = "the price field is required.")
    private Double price;
}
