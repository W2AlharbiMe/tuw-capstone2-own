package com.example.capstone2.Service;

import com.example.capstone2.Api.Exception.ResourceNotFoundException;
import com.example.capstone2.DTO.UpdatePartDTO;
import com.example.capstone2.Model.Part;
import com.example.capstone2.Repository.PartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PartService {

    private final PartRepository partRepository;


    public List<Part> findAll() {
        return partRepository.findAll();
    }

    public Part findById(Integer id) {
        Part part = partRepository.findPartById(id);

        if(part == null) {
            throw new ResourceNotFoundException("part");
        }

        return part;
    }

    public HashMap<String, Object> addPart(Part part) {
        Part savedPart = partRepository.save(part);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the part have been added.");
        response.put("part", part);

        return response;
    }


    public HashMap<String, Object> updatePart(Integer id, UpdatePartDTO updatePartDTO) {
        Part savedPart = findById(id);

        savedPart.setName(updatePartDTO.getName());
        savedPart.setDescription(updatePartDTO.getDescription());
        savedPart.setPurchasePrice(updatePartDTO.getPurchasePrice());
        savedPart.setIsUsed(updatePartDTO.getIsUsed());

        partRepository.save(savedPart);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the part have been updated.");
        response.put("part", savedPart);

        return response;
    }


    public HashMap<String, Object> deletePart(Integer id) {
        Part savedPart = findById(id);

        partRepository.deleteById(id);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the part have been deleted.");
        response.put("part", savedPart);

        return response;
    }
}
