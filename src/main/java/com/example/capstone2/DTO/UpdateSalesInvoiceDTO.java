package com.example.capstone2.DTO;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder(toBuilder = true) // this is used to take default initialized values into account.
public class UpdateSalesInvoiceDTO {

    @NotEmpty(message = "the type field is required.")
    @Pattern(message = "the type can only be `full_payment` or `instalment_payment`", regexp = "(?i)\\b(full_payment|instalment_payment)\\b?")
    private String type;

    @PositiveOrZero(message = "the instalment per month can only be a 0 or positive number.")
    private Double instalmentPerMonth = 0.0; // when type is full_payment then default this to 0

    @NotNull(message = "the car id field is required.")
    private Integer carId;

    @NotNull(message = "the sub price field is required.")
    @Positive(message = "the sub price must be positive number.")
    private Double subPrice; // car price without vat
}
