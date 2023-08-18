package com.example.capstone2.Controller;

import com.example.capstone2.DTO.UpdateSalesPersonDTO;
import com.example.capstone2.Model.SalesPerson;
import com.example.capstone2.Service.SalesPersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sales-persons")
@RequiredArgsConstructor
public class SalesPersonsController {

    private final SalesPersonService salesPersonService;


    @GetMapping("/get")
    public ResponseEntity<List<SalesPerson>> findAll() {
        return ResponseEntity.ok(salesPersonService.findAll());
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<SalesPerson> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(salesPersonService.findById(id));
    }

    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<SalesPerson>> searchByName(@PathVariable String name) {
        return ResponseEntity.ok(salesPersonService.searchByName(name));
    }

    @PostMapping("/add")
    public ResponseEntity<HashMap<String, Object>> addSalesPerson(@RequestBody @Valid SalesPerson salesPerson) {
        return ResponseEntity.status(HttpStatus.CREATED).body(salesPersonService.addSalesPerson(salesPerson));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HashMap<String, Object>> updateSalesPerson(@PathVariable Integer id, @RequestBody @Valid UpdateSalesPersonDTO updateSalesPersonDTO) {
        return ResponseEntity.ok(salesPersonService.updateSalesPerson(id, updateSalesPersonDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteSalesPerson(@PathVariable Integer id) {
        return ResponseEntity.ok(salesPersonService.deleteSalesPerson(id));
    }
}
