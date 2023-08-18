package com.example.capstone2.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateCustomerDTO {

    @NotEmpty(message = "the name field is required.")
    @Size(min = 3, message = "the name must be at least 3 chars.")
    private String name;

    @NotEmpty(message = "the phone number field is required.")
    @Pattern(message = "the entered phone number is invalid.", regexp = "^(009665|9665|\\+9665|05|5)(5|0|3|6|4|9|1|8|7)([0-9]{7})$")
    private String phoneNumber;


    @NotEmpty(message = "the address field is required.")
    private String address;

    @NotEmpty(message = "the city field is required.")
    private String city;

    @NotEmpty(message = "the country field is required.")
    private String country;

    @NotNull(message = "the postal code field is required.")
    private Integer postalCode;

}
