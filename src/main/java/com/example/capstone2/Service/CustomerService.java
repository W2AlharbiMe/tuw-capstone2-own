package com.example.capstone2.Service;

import com.example.capstone2.Api.Exception.ResourceNotFoundException;
import com.example.capstone2.Api.Exception.SimpleException;
import com.example.capstone2.DTO.UpdateCustomerDTO;
import com.example.capstone2.Model.Customer;
import com.example.capstone2.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;


    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(Integer id) throws ResourceNotFoundException {
        Customer customer = customerRepository.findCustomerById(id);

        if(customer == null) {
            throw new ResourceNotFoundException("customer");
        }

        return customer;
    }

    public Customer findByNationalId(String nationalId) throws ResourceNotFoundException {
        Customer customer = customerRepository.findCustomerByNationalIdentity(nationalId);

        if(customer == null) {
            throw new ResourceNotFoundException("customer");
        }

        return customer;
    }

    public Customer findByPhoneNumber(String phoneNumber) throws ResourceNotFoundException {
        Customer customer = customerRepository.findCustomerByPhoneNumber(phoneNumber);

        if(customer == null) {
            throw new ResourceNotFoundException("customer");
        }

        return customer;
    }

    public HashMap<String, Object> createCustomer(Customer customer) throws SimpleException {
        // make sure there's no customer with the same national identity
        if(customerRepository.findCustomerByNationalIdentity(customer.getNationalIdentity()) != null) {
            throw new SimpleException("the customer national identity is used.");
        }

        // make sure there's no customer with the same phone number
        if(customerRepository.findCustomerByPhoneNumber(customer.getPhoneNumber()) != null) {
            throw new SimpleException("the customer phone number is used.");
        }

        Customer saved_customer = customerRepository.save(customer);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the customer have been added.");
        response.put("customer", saved_customer);

        return response;
    }

    public HashMap<String, Object> updateCustomer(Integer id, UpdateCustomerDTO updateCustomerDTO) throws ResourceNotFoundException, SimpleException {
        Customer customer = customerRepository.findCustomerById(id);

        if(customer == null) {
            throw new ResourceNotFoundException("customer");
        }

        // make sure there's no customer with the same phone number
        if(customerRepository.findCustomerByPhoneNumber(updateCustomerDTO.getPhoneNumber()) != null) {
            throw new SimpleException("the customer phone number is used.");
        }

        customer.setName(updateCustomerDTO.getName());
        customer.setCity(updateCustomerDTO.getCity());
        customer.setAddress(updateCustomerDTO.getAddress());
        customer.setPhoneNumber(updateCustomerDTO.getPhoneNumber());
        customer.setPostalCode(updateCustomerDTO.getPostalCode());
        customer.setCountry(updateCustomerDTO.getCountry());

        customerRepository.save(customer);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the customer have been updated.");
        response.put("customer", customer);

        return response;
    }

    public HashMap<String, Object> deleteCustomer(Integer id) throws ResourceNotFoundException {
        Customer customer = customerRepository.findCustomerById(id);

        if(customer == null) {
            throw new ResourceNotFoundException("customer");
        }

        customerRepository.deleteById(id);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the customer have been deleted.");
        response.put("customer", customer);

        return response;
    }
}
