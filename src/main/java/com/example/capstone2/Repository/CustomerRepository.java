package com.example.capstone2.Repository;

import com.example.capstone2.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findCustomerById(Integer id);

    Customer findCustomerByPhoneNumber(String phoneNumber);

    @Query("SELECT c FROM customers c WHERE c.nationalIdentity = ?1")
    Customer findCustomerByNationalIdentity(String nationalIdentity);
}
