package com.example.capstone2.Service;

import com.example.capstone2.Api.Exception.SimpleException;
import com.example.capstone2.DTO.CustomerDTO;
import com.example.capstone2.DTO.SalesPersonDTO;
import com.example.capstone2.Model.Customer;
import com.example.capstone2.Model.SalesPerson;
import com.example.capstone2.Model.User;
import com.example.capstone2.Repository.AuthRepository;
import com.example.capstone2.Repository.CustomerRepository;
import com.example.capstone2.Repository.SalesPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final SalesPersonRepository salesPersonRepository;
    private final CustomerRepository customerRepository;

    public SalesPerson createSalesPerson(SalesPersonDTO salesPersonDTO) {

        if(authRepository.findUserByUsername(salesPersonDTO.getUsername()) != null) {
            throw new SimpleException("use another username.");
        }

        User user = new User();

        user.setUsername(salesPersonDTO.getUsername());

        user.setPassword((new BCryptPasswordEncoder()).encode(salesPersonDTO.getPassword()));
        user.setRole("SALES_PERSON");

        user.setEmail(salesPersonDTO.getEmail());

        User user1 = authRepository.save(user);


        SalesPerson salesPerson = new SalesPerson();

        salesPerson.setActive(salesPersonDTO.getActive());
        salesPerson.setName(salesPersonDTO.getName());
        salesPerson.setSalary(salesPersonDTO.getSalary());
        salesPerson.setUser(user1);

        return salesPersonRepository.save(salesPerson);
    }


}
