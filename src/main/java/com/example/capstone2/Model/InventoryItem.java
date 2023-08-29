package com.example.capstone2.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "inventory_items")
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty(message = "the type field is required.")
    @Pattern(message = "the type can only be `car` or `part`", regexp = "(?i)\\b(car|part)\\b?")
    @Column(nullable = false)
    private String type; // car or part ?


    @NotNull(message = "the quantity field is required.")
    @Positive(message = "the quantity must be positive number.")
    @Min(value = 5, message = "the quantity must be at least 5.")
    @Column(nullable = false)
    private Integer quantity; // you can not store more than < maxCapacity


    // Relations

    @ManyToOne
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    @JsonIgnore
    private Inventory inventory;


    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;


    @ManyToOne
    @JoinColumn(name = "part_id", referencedColumnName = "id")
    private Part part;

}
