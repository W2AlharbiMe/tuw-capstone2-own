package com.example.capstone2.Service;

import com.example.capstone2.Api.Exception.ResourceNotFoundException;
import com.example.capstone2.Api.Exception.SimpleException;
import com.example.capstone2.DTO.UpdateSalesPersonDTO;
import com.example.capstone2.Model.SalesPerson;
import com.example.capstone2.Repository.SalesPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SalesPersonService {

    private final SalesPersonRepository salesPersonRepository;

    public List<SalesPerson> findAll() {
        return salesPersonRepository.findAll();
    }

    public List<SalesPerson> searchByName(String name) throws ResourceNotFoundException {
        List<SalesPerson> salesPeople = salesPersonRepository.lookByName(name);

        if(salesPeople.isEmpty()) {
            throw new ResourceNotFoundException("sales persons");
        }

        return salesPeople;
    }

    public SalesPerson findById(Integer id) throws ResourceNotFoundException {
        SalesPerson salesPerson = salesPersonRepository.findSalesPersonById(id);

        if(salesPerson == null) {
            throw new ResourceNotFoundException("sales person");
        }

        return salesPerson;
    }

    public HashMap<String, Object> addSalesPerson(SalesPerson salesPerson) throws SimpleException {
        if(salesPersonRepository.findSalesPersonByUsername(salesPerson.getUsername()) != null) {
            throw new SimpleException("the username is invalid.");
        }

        SalesPerson salesPerson1 = salesPersonRepository.save(salesPerson);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the sales person have been added.");
        response.put("salesPerson", salesPerson1);

        return response;
    }
    public HashMap<String, Object> updateSalesPerson(Integer id, UpdateSalesPersonDTO updateSalesPersonDTO) throws ResourceNotFoundException, SimpleException {
        SalesPerson salesPerson = salesPersonRepository.findSalesPersonById(id);

        if(salesPerson == null) {
            throw new ResourceNotFoundException("sales person");
        }

        // optimization tick. only look in the database when the username changes.
        if(!Objects.equals(salesPerson.getUsername(), updateSalesPersonDTO.getUsername())) {
            if(salesPersonRepository.findSalesPersonByUsername(updateSalesPersonDTO.getUsername()) != null) {
                throw new SimpleException("the username is invalid.");
            }
        }

        salesPerson.setName(updateSalesPersonDTO.getName());
        salesPerson.setUsername(updateSalesPersonDTO.getUsername());
        salesPerson.setPassword(updateSalesPersonDTO.getPassword());
        salesPerson.setSalary(updateSalesPersonDTO.getSalary());


        salesPersonRepository.save(salesPerson);


        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the sales person have been updated.");
        response.put("salesPerson", salesPerson);

        return response;
    }

    public HashMap<String, Object> deleteSalesPerson(Integer id) throws ResourceNotFoundException {
        SalesPerson salesPerson = salesPersonRepository.findSalesPersonById(id);

        if(salesPerson == null) {
            throw new ResourceNotFoundException("sales person");
        }

        salesPersonRepository.deleteById(id);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the sales person have been deleted.");
        response.put("salesPerson", salesPerson);

        return response;
    }
}
