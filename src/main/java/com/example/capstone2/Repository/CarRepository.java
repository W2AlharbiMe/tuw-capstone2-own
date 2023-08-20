package com.example.capstone2.Repository;

import com.example.capstone2.Model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    public Car findCarById(Integer id);

    @Query("SELECT c.id FROM cars c WHERE c.manufacturerId = ?1 ORDER BY c.id ASC LIMIT 1")
    public Car findAtLeastOneManufacturerId(Integer manufacturerId);


    @Query("SELECT c FROM cars c WHERE c.manufacturerId = ?1")
    public List<Car> findAllManufacturerCars(Integer manufacturerId);


}
