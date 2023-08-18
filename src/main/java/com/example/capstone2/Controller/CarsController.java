package com.example.capstone2.Controller;

import com.example.capstone2.Api.Exception.ResourceNotFoundException;
import com.example.capstone2.Api.Exception.SimpleException;
import com.example.capstone2.DTO.AddCarDTO;
import com.example.capstone2.DTO.UpdateCarDTO;
import com.example.capstone2.Model.Car;
import com.example.capstone2.Service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarsController {
    private final CarService carService;

    @GetMapping("/get")
    public ResponseEntity<List<Car>> findAll() {
        return ResponseEntity.ok(carService.findAll());
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<Car> findById(@PathVariable Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(carService.findCarById(id));
    }

    @GetMapping("/search/serial-number/{serialNumber}")
    public ResponseEntity<Car> findById(@PathVariable String serialNumber) throws ResourceNotFoundException {
        return ResponseEntity.ok(carService.findCarBySerialNumber(serialNumber));
    }

    @GetMapping("/manufacturer/{manufacturerId}")
    public ResponseEntity<List<Car>> findCarsByManufacturerId(@PathVariable Integer manufacturerId) throws SimpleException {
        return ResponseEntity.ok(carService.findCarsByManufacturerId(manufacturerId));
    }

    @PostMapping("/add")
    public ResponseEntity<HashMap<String, Object>> addCar(@RequestBody @Valid AddCarDTO addCarDTO) throws SimpleException {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.addCar(addCarDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HashMap<String, Object>> updateCar(@PathVariable Integer id, @RequestBody @Valid UpdateCarDTO updateCarDTO) throws ResourceNotFoundException, SimpleException {
        return ResponseEntity.ok(carService.updateCar(id, updateCarDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteCar(@PathVariable Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(carService.deleteCar(id));
    }
}
