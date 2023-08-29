package com.example.capstone2.Service;

import com.example.capstone2.Api.Exception.ResourceNotFoundException;
import com.example.capstone2.Api.Exception.SimpleException;
import com.example.capstone2.DTO.UpdateManufacturerDTO;
import com.example.capstone2.Model.Manufacturer;
import com.example.capstone2.Repository.CarRepository;
import com.example.capstone2.Repository.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;
    private final CarRepository carRepository;


    public List<Manufacturer> findAll() {
        return manufacturerRepository.findAll();
    }

    public Manufacturer findById(Integer id) throws ResourceNotFoundException {
        Manufacturer manufacturer = manufacturerRepository.findManufacturerById(id);

        if(manufacturer == null) {
            throw new ResourceNotFoundException("manufacturer");
        }

        return manufacturer;
    }

    public void manufacturerExists(Integer id) throws SimpleException {
        if(manufacturerRepository.findManufacturerById(id) == null) {
            throw new SimpleException("no manufacturer found with the manufacturer id you provided.");
        }
    }
//
    public List<Manufacturer> searchByName(String name) throws ResourceNotFoundException {
        List<Manufacturer> manufacturers = manufacturerRepository.lookByName(name);

        if(manufacturers.isEmpty()) {
            throw new ResourceNotFoundException("manufacturer");
        }

        return manufacturers;
    }
//
    public HashMap<String, Object> addManufacturer(Manufacturer manufacturer) throws SimpleException {
        if(manufacturerRepository.findManufacturerByName(manufacturer.getName()) != null) {
            throw new SimpleException("there's a manufacturer with the same name.");
        }

        Manufacturer saved_manufacturer = manufacturerRepository.save(manufacturer);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the manufacturer have been added.");
        response.put("manufacturer", saved_manufacturer);

        return response;
    }

    public HashMap<String, Object> updateManufacturer(Integer id, UpdateManufacturerDTO manufacturerDTO) throws ResourceNotFoundException {
        Manufacturer old_manufacturer = manufacturerRepository.findManufacturerById(id);

        if(old_manufacturer == null) {
            throw new ResourceNotFoundException("manufacturer");
        }

        old_manufacturer.setName(manufacturerDTO.getName());
        old_manufacturer.setEstablishmentYear(manufacturerDTO.getEstablishmentYear());

        manufacturerRepository.save(old_manufacturer);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the manufacturer have been updated.");
        response.put("manufacturer", old_manufacturer);

        return response;
    }

    public HashMap<String, Object> deleteManufacturer(Integer id) throws ResourceNotFoundException, SimpleException {
        Manufacturer manufacturer = manufacturerRepository.findManufacturerById(id);

        if(manufacturer == null) {
            throw new ResourceNotFoundException("manufacturer");
        }


        if(carRepository.findAtLeastOneManufacturerId(id) != null) {
            throw new SimpleException("you cannot delete this manufacturer because there are cars that have been manufactured by them.");
        }

        manufacturerRepository.deleteById(id);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the manufacturer have been deleted.");
        response.put("manufacturer", manufacturer);

        return response;
    }



}
