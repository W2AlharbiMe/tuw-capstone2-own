package com.example.capstone2.Service;

import com.example.capstone2.Api.Exception.ResourceNotFoundException;
import com.example.capstone2.Api.Exception.SimpleException;
import com.example.capstone2.DTO.AddCarDTO;
import com.example.capstone2.DTO.UpdateCarDTO;
import com.example.capstone2.Model.Car;
import com.example.capstone2.Model.Manufacturer;
import com.example.capstone2.Repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final ManufacturerService manufacturerService;

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car findCarById(Integer id) throws ResourceNotFoundException {
        Car car = carRepository.findCarById(id);

        if(car == null) {
            throw new ResourceNotFoundException("car");
        }

        return car;
    }

    public Car findCarBySerialNumber(String serialNumber) throws ResourceNotFoundException {
        Car car = carRepository.findCarBySerialNumber(serialNumber);

        if(car == null) {
            throw new ResourceNotFoundException("car");
        }

        return car;
    }

    public List<Car> findCarsByManufacturerId(Integer manufacturerId) throws SimpleException {
        List<Car> cars = carRepository.findAllManufacturerCars(manufacturerId);

        if(cars.isEmpty()) {
            throw new SimpleException("no cars found with the manufacturer id you provided.");
        }

        return cars;
    }

    public HashMap<String, Object> addCar(AddCarDTO addCarDTO) throws SimpleException {
        // make sure that there provided manufacturerId exists.
        manufacturerService.manufacturerExists(addCarDTO.getManufacturerId());


        Car car = new Car();

        car.setType(addCarDTO.getType().toLowerCase()); // ensure stored type not like SaDeN etc...
        car.setColor(addCarDTO.getColor().toLowerCase()); // ensure stored color not like ReD, GrEen etc...
        car.setModel(addCarDTO.getModel());
        car.setReleaseYear(addCarDTO.getReleaseYear());
        car.setSeatsCount(addCarDTO.getSeatsCount());
        car.setManufacturerId(addCarDTO.getManufacturerId());
        car.generateSerialNumber();

        Car saved_car = carRepository.save(car);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the car have been added.");
        response.put("car", saved_car);

        return response;
    }

    public HashMap<String, Object> updateCar(Integer id, UpdateCarDTO updateCarDTO) throws ResourceNotFoundException, SimpleException {
        Car car = carRepository.findCarById(id);

        if(car == null) {
            throw new ResourceNotFoundException("car");
        }

        // this will throw SimpleException in case no Manufacturer with manufacturer id
        manufacturerService.manufacturerExists(updateCarDTO.getManufacturerId());

        car.setModel(updateCarDTO.getModel());
        car.setType(updateCarDTO.getType());
        car.setColor(updateCarDTO.getColor());
        car.setSeatsCount(updateCarDTO.getSeatsCount());
        car.setReleaseYear(updateCarDTO.getReleaseYear());
        car.setManufacturerId(updateCarDTO.getManufacturerId());

        carRepository.save(car);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the car have been updated.");
        response.put("car", car);

        return response;
    }

    public HashMap<String, Object> deleteCar(Integer id) throws ResourceNotFoundException {
        Car car = carRepository.findCarById(id);

        if(car == null) {
            throw new ResourceNotFoundException("car");
        }

        carRepository.deleteById(id);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the car have been deleted.");
        response.put("car", car);

        return response;
    }
}
