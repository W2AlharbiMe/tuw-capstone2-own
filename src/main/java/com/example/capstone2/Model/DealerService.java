package com.example.capstone2.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "dealer_services")
public class DealerService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "the name field is required.")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "the price field is required.")
    @Column(nullable = false)
    private Double price;



    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dealerService")
    @JsonIgnore
    private Set<SalesInvoice> salesInvoices;
}
