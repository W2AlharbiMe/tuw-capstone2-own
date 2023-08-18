package com.example.capstone2.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateManufacturerDTO {

    @NotEmpty(message = "the name field is required.")
    @Size(min = 3, message = "the name must be at least 3 chars.")
    private String name;

    @NotNull(message = "the establishment year field is required.")
    @Min(message = "invalid establishment year it must be 4 digits and a valid year.", value = 1900)
    private Integer  establishmentYear;
}
