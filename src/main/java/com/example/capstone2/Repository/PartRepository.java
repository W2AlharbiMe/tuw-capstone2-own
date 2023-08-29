package com.example.capstone2.Repository;

import com.example.capstone2.Model.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<Part, Integer> {

    Part findPartById(Integer id);

}
