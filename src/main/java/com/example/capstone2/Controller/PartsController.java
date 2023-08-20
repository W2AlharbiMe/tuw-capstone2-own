package com.example.capstone2.Controller;

import com.example.capstone2.DTO.UpdatePartDTO;
import com.example.capstone2.Model.Car;
import com.example.capstone2.Model.Part;
import com.example.capstone2.Service.PartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/parts")
@RequiredArgsConstructor
public class PartsController {

    private final PartService partService;

    @GetMapping("/get")
    public ResponseEntity<List<Part>> findAll() {
        return ResponseEntity.ok(partService.findAll());
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<Part> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(partService.findById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<HashMap<String, Object>> addPart(@RequestBody @Valid Part part) {
        return ResponseEntity.ok(partService.addPart(part));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HashMap<String, Object>> updatePart(@PathVariable Integer id, @RequestBody @Valid UpdatePartDTO updatePartDTO) {
        return ResponseEntity.ok(partService.updatePart(id, updatePartDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HashMap<String, Object>> deletePart(@PathVariable Integer id) {
        return ResponseEntity.ok(partService.deletePart(id));
    }

}
