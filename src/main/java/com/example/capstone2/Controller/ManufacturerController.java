package com.example.capstone2.Controller;

import com.example.capstone2.DTO.UpdateManufacturerDTO;
import com.example.capstone2.Model.Manufacturer;
import com.example.capstone2.Service.ManufacturerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/manufacturers")
@RequiredArgsConstructor
public class ManufacturerController {
    private final ManufacturerService manufacturerService;

    @GetMapping("/get")
    public ResponseEntity<List<Manufacturer>> findAll() {
        return ResponseEntity.ok(manufacturerService.findAll());
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<Manufacturer> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(manufacturerService.findById(id));
    }

    // /api/v1/manufacturers/search?name=abcd
    @GetMapping("/search")
    public ResponseEntity<List<Manufacturer>> searchByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(manufacturerService.searchByName(name));
    }

    @PostMapping("/add")
    public ResponseEntity<HashMap<String, Object>> addManufacturer(@RequestBody @Valid Manufacturer manufacturer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(manufacturerService.addManufacturer(manufacturer));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HashMap<String, Object>> updateManufacturer(@PathVariable Integer id, @RequestBody @Valid UpdateManufacturerDTO updateManufacturerDTO) {
        return ResponseEntity.ok(manufacturerService.updateManufacturer(id, updateManufacturerDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteManufacturer(@PathVariable Integer id) {
        return ResponseEntity.ok(manufacturerService.deleteManufacturer(id));
    }
}
