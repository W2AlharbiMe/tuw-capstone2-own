package com.example.capstone2.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class UpdateCarDTO {

    @NotEmpty(message = "the model field is required.")
    @Size(min = 4, message = "the model must be at least 4 chars.")
    private String model;

    /**
     * Sedan
     * Sport
     * Van
     * Hatchback
     * SUV
     * Minivan
     * Truck
     */
    @NotEmpty(message = "the type field is required.")
    @Pattern(message = "the type can only be `sedan`, `sport`, `van`, `hatchback`, `suv`, `minivan`, `truck`.", regexp = "(?i)\\b(sedan|sport|van|hatchback|suv|minivan|truck)\\b?")
    private String type;

    @NotEmpty(message = "the color field is required.")
    private String color;


    @NotNull(message = "the seats count is required.")
    @Positive(message = "the seats count must be positive number.")
    private Integer seatsCount;

    @NotEmpty(message = "the release year field is required.")
    @Digits(integer = 4, fraction = 0, message = "the release year must be 4 digits.")
    private String releaseYear;

    @NotNull(message = "the manufacturer id field is required.")
    private Integer manufacturerId;
}
