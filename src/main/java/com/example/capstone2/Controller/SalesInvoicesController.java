package com.example.capstone2.Controller;

import com.example.capstone2.DTO.UpdateSalesInvoiceDTO;
import com.example.capstone2.Model.SalesInvoice;
import com.example.capstone2.Service.SalesInvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    // POST /api/v1/sales-invoices/add?username=abdullah&password=SuperSecretPassWorD
    @PostMapping("/add")
    public ResponseEntity<HashMap<String, Object>> addSalesInvoice(@RequestBody @Valid SalesInvoice salesInvoice, @RequestParam("username") String username, @RequestParam("password") String password) {
        return ResponseEntity.status(HttpStatus.CREATED).body(salesInvoiceService.addSalesInvoice(salesInvoice, username, password));
    }

    // update salesInvoice by id
    // PUT /api/v1/sales-invoices/update/id/{id}?username=abdullah&password=SuperSecretPassWorD
    @PutMapping("/update/id/{id}")
    public ResponseEntity<HashMap<String, Object>> addSalesInvoiceById(@PathVariable Integer id, @RequestBody @Valid UpdateSalesInvoiceDTO updateSalesInvoiceDTO, @RequestParam("username") String username, @RequestParam("password") String password) {
        return ResponseEntity.status(HttpStatus.CREATED).body(salesInvoiceService.updateSalesInvoiceById(id, updateSalesInvoiceDTO, username, password));
    }

    // update salesInvoice by invoiceUUID
    // PUT /api/v1/sales-invoices/update/invoiceUUID/{invoiceUUID}?username=abdullah&password=SuperSecretPassWorD
    @PutMapping("/update/invoiceUUID/{invoiceUUID}")
    public ResponseEntity<HashMap<String, Object>> addSalesInvoiceByInvoiceUUID(@PathVariable String invoiceUUID, @RequestBody @Valid UpdateSalesInvoiceDTO updateSalesInvoiceDTO, @RequestParam("username") String username, @RequestParam("password") String password) {
        return ResponseEntity.ok(salesInvoiceService.updateSalesInvoiceByInvoiceUUID(invoiceUUID, updateSalesInvoiceDTO, username, password));
    }

    // DELETE /api/v1/sales-invoices/delete/{id}?username=abdullah&password=SuperSecretPassWorD
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteSalesInvoice(@PathVariable Integer id, @RequestParam("username") String username, @RequestParam("password") String password) {
        return ResponseEntity.ok(salesInvoiceService.deleteSalesInvoice(id, username, password));
    }
}
