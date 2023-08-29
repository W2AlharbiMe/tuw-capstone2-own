package com.example.capstone2.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalesInvoiceDTO {

    @NotEmpty(message = "the type field is required.")
    @Pattern(message = "the type can only be `full_car_payment`, or `instalment_car_payment`, `full_service_payment`, ``, `instalment_service_payment`.", regexp = "(?i)\\b(full_car_payment|instalment_car_payment|full_service_payment|instalment_service_payment)\\b?")
    private String type;


    @NotEmpty(message = "the status field is required.")
    @Pattern(message = "the status can only be `pending` or `paid`.", regexp = "(?i)\\b(pending|paid)\\b?")
    private String status;

    @PositiveOrZero(message = "the instalment per month can only be a 0 or positive number.")
    private Double instalmentPerMonth = 0.0; // when type is full_payment then default this to 0

    private Double vat = 0.15;


    @NotNull(message = "the car id field is required.")
    private Integer carId;


    @NotNull(message = "the customer id field is required.")
    private Integer customerId;


    @NotNull(message = "the sales person id field is required.")
    private Integer salesPersonId;


    @NotNull(message = "the dealer service id field is required.")
    private Integer dealerServiceId;

    @NotNull(message = "the serial number id field is required.")
    private Integer serialNumberId;


}
