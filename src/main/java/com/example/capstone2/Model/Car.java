package com.example.capstone2.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "the model field is required.")
    @Size(min = 4, message = "the model must be at least 4 chars.")
    @Column(nullable = false)
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
    @Column(columnDefinition = "varchar(9) not null check (type in ('sedan', 'sport', 'van', 'hatchback', 'suv', 'minivan', 'truck'))")
    private String type;

    @NotEmpty(message = "the color field is required.")
    @Column(nullable = false)
    private String color;


    @NotNull(message = "the seats count is required.")
    @Positive(message = "the seats count must be positive number.")
    @Column(nullable = false)
    private Integer seatsCount;


    @NotEmpty(message = "the release year field is required.")
    @Digits(integer = 4, fraction = 0, message = "the release year must be 4 digits.")
    @Column(nullable = false)
    private String releaseYear;


    // Relation

    @ManyToOne
    @JoinColumn(name = "manufacturer_id", referencedColumnName = "id")
    @JsonIgnore
    private Manufacturer manufacturer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "car")
    @JsonIgnore
    private Set<InventoryItem> inventoryItems;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "car")
    @JsonIgnore
    private Set<SerialNumber> serialNumbers;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "car")
    @JsonIgnore
    private Set<SalesInvoice> salesInvoices;

}
