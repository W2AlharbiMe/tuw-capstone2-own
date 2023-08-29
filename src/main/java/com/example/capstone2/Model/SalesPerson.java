package com.example.capstone2.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sales_persons")
public class SalesPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "the name field is required.")
    @Column(nullable = false)
    private String name;


    @NotNull(message = "the salary field is required.")
    @Positive(message = "the salary must be positive number.")
    @Column(nullable = false)
    private Double salary;


    @Column(columnDefinition = "bit(1) default 1")
    private Boolean active = true;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    private User user;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "salesperson")
    @JsonIgnore
    private Set<SalesInvoice> salesInvoices;

}
