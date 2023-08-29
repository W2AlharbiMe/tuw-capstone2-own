package com.example.capstone2.Repository;

import com.example.capstone2.Model.SalesPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesPersonRepository extends JpaRepository<SalesPerson, Integer> {

    SalesPerson findSalesPersonById(Integer id);

    @Query("SELECT c FROM sales_persons c WHERE c.name LIKE '%' || ?1 || '%' ")
    public List<SalesPerson> lookByName(String name);


}
