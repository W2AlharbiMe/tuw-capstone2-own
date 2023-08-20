package com.example.capstone2.Controller;

import com.example.capstone2.DTO.UpdateDealerServiceDTO;
import com.example.capstone2.DTO.UpdatePartDTO;
import com.example.capstone2.Model.DealerService;
import com.example.capstone2.Model.Part;
import com.example.capstone2.Service.DealerServiceService;
import com.example.capstone2.Service.PartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dealer-services")
@RequiredArgsConstructor
public class DealerServiceController {

    private final DealerServiceService dealerServiceService;

    @GetMapping("/get")
    public ResponseEntity<List<DealerService>> findAll() {
        return ResponseEntity.ok(dealerServiceService.findAll());
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<DealerService> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(dealerServiceService.findById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<HashMap<String, Object>> addPart(@RequestBody @Valid DealerService service) {
        return ResponseEntity.ok(dealerServiceService.addService(service));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HashMap<String, Object>> updatePart(@PathVariable Integer id, @RequestBody @Valid UpdateDealerServiceDTO updateDealerServiceDTO) {
        return ResponseEntity.ok(dealerServiceService.updatePart(id, updateDealerServiceDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HashMap<String, Object>> deletePart(@PathVariable Integer id) {
        return ResponseEntity.ok(dealerServiceService.deletePart(id));
    }

}
