package com.example.capstone2.DTO;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateSalesPersonDTO {
    @NotEmpty(message = "the name field is required.")
    private String name;


    @NotEmpty(message = "the username field is required.")
    private String username;

    @NotEmpty(message = "the password field is required.")
    @Pattern(message = "the password must contain at least seven characters, at least one number and both lower and uppercase letters and special characters", regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
    private String password;

    @NotNull(message = "the salary field is required.")
    private Double salary;

    @NotNull(message = "the field active is required.")
    private Boolean active;
}
