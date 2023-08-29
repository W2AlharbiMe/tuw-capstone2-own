package com.example.capstone2.Service;

import com.example.capstone2.Api.Exception.ResourceNotFoundException;
import com.example.capstone2.DTO.UpdateDealerServiceDTO;
import com.example.capstone2.Model.DealerService;
import com.example.capstone2.Repository.DealerServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DealerServiceService {

    private final DealerServiceRepository dealerServiceRepository;


    public List<DealerService> findAll() {
        return dealerServiceRepository.findAll();
    }

    public DealerService findById(Integer id) {
        DealerService dealerService = dealerServiceRepository.findDealerServiceById(id);

        if(dealerService == null) {
            throw new ResourceNotFoundException("dealer service");
        }

        return dealerService;
    }

    public HashMap<String, Object> addService(DealerService dealerService) {
        DealerService dealerService1 = dealerServiceRepository.save(dealerService);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the dealer service have been added.");
        response.put("dealerService", dealerService1);

        return response;
    }


    public HashMap<String, Object> updatePart(Integer id, UpdateDealerServiceDTO updateDealerServiceDTO) {
        DealerService savedDealerService = findById(id);

        savedDealerService.setName(updateDealerServiceDTO.getName());
        savedDealerService.setPrice(updateDealerServiceDTO.getPrice());

        dealerServiceRepository.save(savedDealerService);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the part have been updated.");
        response.put("dealerService", savedDealerService);

        return response;
    }



    public HashMap<String, Object> deletePart(Integer id) {
        DealerService savedDealerService = findById(id);


        dealerServiceRepository.deleteById(id);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the dealer service have been deleted.");
        response.put("dealerService", savedDealerService);

        return response;
    }

}
