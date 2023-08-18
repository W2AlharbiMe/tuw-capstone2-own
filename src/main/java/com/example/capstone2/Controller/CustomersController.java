package com.example.capstone2.Controller;

import com.example.capstone2.DTO.UpdateCustomerDTO;
import com.example.capstone2.Model.Customer;
import com.example.capstone2.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomersController {

    private final CustomerService customerService;

    @GetMapping("/get")
    public ResponseEntity<List<Customer>> findAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<Customer> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(customerService.findById(id));
    }


    @GetMapping("/search/national-identity/{nationalIdentity}")
    public ResponseEntity<Customer> findByNationalIdentity(@PathVariable String nationalIdentity) {
        return ResponseEntity.ok(customerService.findByNationalId(nationalIdentity));
    }


    @GetMapping("/search/phone-number/{phoneNumber}")
    public ResponseEntity<Customer> findByPhoneNumber(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(customerService.findByPhoneNumber(phoneNumber));
    }

    @PostMapping("/create")
    public ResponseEntity<HashMap<String, Object>> createCustomer(@RequestBody @Valid Customer customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customer));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HashMap<String, Object>> updateCustomer(@PathVariable Integer id, @RequestBody @Valid UpdateCustomerDTO updateCustomerDTO) {
        return ResponseEntity.ok(customerService.updateCustomer(id, updateCustomerDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteCustomer(@PathVariable Integer id) {
        return ResponseEntity.ok(customerService.deleteCustomer(id));
    }

}
