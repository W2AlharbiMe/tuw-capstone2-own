package com.example.capstone2.Repository;

import com.example.capstone2.Model.SerialNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serial;
import java.util.List;

@Repository
public interface SerialNumberRepository extends JpaRepository<SerialNumber, Integer> {

    public SerialNumber findSerialNumberById(Integer id);

    public SerialNumber findCarBySerialNumber(String serialNumber);

    @Query("SELECT s FROM serial_numbers s WHERE s.car.id = ?1 AND s.isUsed = false ORDER BY s.id DESC LIMIT 1")
    SerialNumber latestUnusedSerialNumberByCarId(Integer carId);

}
