package com.example.capstone2.Repository;

import com.example.capstone2.Model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {

    public Manufacturer findManufacturerById(Integer id);

    public Manufacturer findManufacturerByName(String name);

    @Query("SELECT m FROM manufacturers m WHERE m.name LIKE '%' || ?1 || '%' ")
    public List<Manufacturer> lookByName(String name);


}
