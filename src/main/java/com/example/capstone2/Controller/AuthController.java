package com.example.capstone2.Controller;


import com.example.capstone2.Api.Response.ApiResponseWithData;
import com.example.capstone2.Api.Response.SimpleApiResponse;
import com.example.capstone2.DTO.CustomerDTO;
import com.example.capstone2.DTO.SalesPersonDTO;
import com.example.capstone2.Model.Customer;
import com.example.capstone2.Model.SalesPerson;
import com.example.capstone2.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // I know that I should create 1 sign up that will create the user
    // and then the other endpoints to update the customer however due to the insane deadline I decided doing it this way.
    @PostMapping("/create/customer")
    public ResponseEntity<ApiResponseWithData<Customer>> createCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        return ResponseEntity.ok((new ApiResponseWithData<>("created.", authService.createCustomer(customerDTO))));
    }

    @PostMapping("/create/sales-person")
    public ResponseEntity<ApiResponseWithData<SalesPerson>> createSalesPerson(@RequestBody @Valid SalesPersonDTO salesPersonDTO) {
        return ResponseEntity.ok((new ApiResponseWithData<>("created.", authService.createSalesPerson(salesPersonDTO))));
    }

    @GetMapping("/logout")
    public ResponseEntity<SimpleApiResponse> logout() {
        return ResponseEntity.ok(new SimpleApiResponse("logged out successfully."));
    }
}
