package com.example.capstone2.Service;

import com.example.capstone2.Api.Exception.SimpleException;
import com.example.capstone2.Model.Car;
import com.example.capstone2.Model.Manufacturer;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManufacturerCarService {

    private final ManufacturerService manufacturerService;
    private final CarService carService;


    public void ensureManufacturerHaveOneCar(Integer manufacturerId) throws SimpleException {
        if(!carService.manufacturerHaveOneCar(manufacturerId)) {
            throw new SimpleException("you cannot delete this manufacturer because there are cars that have been manufactured by them.");
        }
    }

    public void ensureManufacturerExists(Integer manufacturerId) throws SimpleException {
        // make sure that there's a manufacturer. when creating a car
        manufacturerService.manufacturerExists(manufacturerId);
    }
}
