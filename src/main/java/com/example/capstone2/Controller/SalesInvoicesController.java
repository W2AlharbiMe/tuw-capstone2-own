package com.example.capstone2.Controller;

import com.example.capstone2.DTO.SalesInvoiceDTO;
import com.example.capstone2.DTO.UpdateSalesInvoiceDTO;
import com.example.capstone2.Model.SalesInvoice;
import com.example.capstone2.Model.User;
import com.example.capstone2.Service.SalesInvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sales-invoices")
@RequiredArgsConstructor
public class SalesInvoicesController {

    private final SalesInvoiceService salesInvoiceService;

    @GetMapping("/get")
    public ResponseEntity<List<SalesInvoice>> findAll() {
        return ResponseEntity.ok(salesInvoiceService.findAll());
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<SalesInvoice> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(salesInvoiceService.findById(id));
    }

    @GetMapping("/search/invoice-uuid/{invoiceUUID}")
    public ResponseEntity<SalesInvoice> findByInvoiceUUID(@PathVariable String invoiceUUID) {
        return ResponseEntity.ok(salesInvoiceService.findByInvoiceUUID(invoiceUUID));
    }

    @GetMapping("/search/type/{type}")
    public ResponseEntity<List<SalesInvoice>> findByType(@PathVariable String type) {
        return ResponseEntity.ok(salesInvoiceService.findByType(type));
    }


    @GetMapping("/search/customer-id/{customerId}")
    public ResponseEntity<List<SalesInvoice>> findByCustomerId(@PathVariable Integer customerId) {
        return ResponseEntity.ok(salesInvoiceService.findByCustomerId(customerId));
    }

    @GetMapping("/search/car-id/{carId}")
    public ResponseEntity<SalesInvoice> findByCarId(@PathVariable Integer carId) {
        return ResponseEntity.ok(salesInvoiceService.lookForSalesByCarId(carId));
    }


    @GetMapping("/search/sales-person-id/{salesPersonId}")
    public ResponseEntity<List<SalesInvoice>> findBySalesPersonId(@PathVariable Integer salesPersonId) {
        return ResponseEntity.ok(salesInvoiceService.lookBySalesPersonId(salesPersonId));
    }

    @GetMapping("/search/customer-car-id/{customerId}/{carId}")
    public ResponseEntity<SalesInvoice> lookForSalesInvoiceByCustomerIdAndCarId(@PathVariable Integer customerId, @PathVariable Integer carId) {
        return ResponseEntity.ok(salesInvoiceService.lookForSalesInvoiceByCustomerIdAndCarId(customerId, carId));
    }


    @GetMapping("/search/customer-car-sales-person-id/{customerId}/{carId}/{salesPersonId}")
    public ResponseEntity<SalesInvoice> lookByCustomerIdAndCarIdAndSalesPersonId(@PathVariable Integer customerId, @PathVariable Integer carId, @PathVariable Integer salesPersonId) {
        return ResponseEntity.ok(salesInvoiceService.lookByCustomerIdAndCarIdAndSalesPersonId(customerId, carId, salesPersonId));
    }

    @GetMapping("/search/car-serial-number/{serialNumber}")
    public ResponseEntity<HashMap<String, Object>> findBySerialNumber(@PathVariable String serialNumber) {
        return ResponseEntity.ok(salesInvoiceService.findBySerialNumber(serialNumber));
    }


    // POST /api/v1/sales-invoices/add?username=abdullah&password=SuperSecretPassWorD
    @PostMapping("/add")
    public ResponseEntity<HashMap<String, Object>> addSalesInvoice(@RequestBody @Valid SalesInvoiceDTO salesInvoiceDTO,
                                                                   @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(salesInvoiceService.addSalesInvoice(salesInvoiceDTO, user));
    }


    @PutMapping("/pay-invoice/{id}")
    public ResponseEntity<HashMap<String, Object>> payInvoice(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(salesInvoiceService.payInvoice(id));
    }

}
