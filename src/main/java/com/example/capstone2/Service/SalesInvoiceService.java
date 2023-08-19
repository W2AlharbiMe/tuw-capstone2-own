package com.example.capstone2.Service;

import com.example.capstone2.Api.Exception.ResourceNotFoundException;
import com.example.capstone2.Api.Exception.SimpleException;
import com.example.capstone2.DTO.UpdateSalesInvoiceDTO;
import com.example.capstone2.Model.SalesInvoice;
import com.example.capstone2.Model.SalesPerson;
import com.example.capstone2.Repository.SalesInvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SalesInvoiceService {

    private final SalesInvoiceRepository salesInvoiceRepository;
    private final CarService carService;
    private final CustomerService customerService;
    private final SalesPersonService salesPersonService;


    public List<SalesInvoice> findAll() {
        return salesInvoiceRepository.findAll();
    }

    public SalesInvoice findById(Integer id) throws ResourceNotFoundException {
        SalesInvoice salesInvoice = salesInvoiceRepository.findSalesInvoiceById(id);

        if(salesInvoice == null) {
            throw new ResourceNotFoundException("sales invoice");
        }

        return salesInvoice;
    }

    public SalesInvoice findByInvoiceUUID(String invoiceUUID) throws ResourceNotFoundException {
        SalesInvoice salesInvoice = salesInvoiceRepository.findSalesInvoiceByInvoiceUUID(invoiceUUID);

        if(salesInvoice == null) {
            throw new ResourceNotFoundException("sales invoice");
        }

        return salesInvoice;
    }

    public List<SalesInvoice> findByCustomerId(Integer customerId) throws ResourceNotFoundException {
        List<SalesInvoice> salesInvoice = salesInvoiceRepository.findSalesInvoicesByCustomerId(customerId);

        if(salesInvoice.isEmpty()) {
            throw new ResourceNotFoundException("sales invoice");
        }

        return salesInvoice;
    }
    public List<SalesInvoice> findByType(String type) throws ResourceNotFoundException, SimpleException {
        if(!(type.equalsIgnoreCase("full_payment") || type.equalsIgnoreCase("instalment_payment"))) {
            throw new SimpleException("invalid type. it can only be 'full_payment' or 'instalment_payment'.");
        }

        List<SalesInvoice> salesInvoice = salesInvoiceRepository.findSalesInvoiceByType(type);

        if(salesInvoice.isEmpty()) {
            throw new ResourceNotFoundException("sales invoice");
        }

        return salesInvoice;
    }

    public SalesInvoice lookForSalesByCarId(Integer carId) throws ResourceNotFoundException {
        SalesInvoice salesInvoice = salesInvoiceRepository.lookForSalesByCarId(carId);

        if(salesInvoice == null) {
            throw new ResourceNotFoundException("sales invoice");
        }

        return salesInvoice;
    }

    public List<SalesInvoice> lookBySalesPersonId(Integer salesPersonId) throws ResourceNotFoundException {
        List<SalesInvoice> salesInvoice = salesInvoiceRepository.lookForSalesInvoicesBySalesPersonId(salesPersonId);

        if(salesInvoice.isEmpty()) {
            throw new ResourceNotFoundException("sales invoice");
        }

        return salesInvoice;
    }

    public SalesInvoice lookByCustomerIdAndCarIdAndSalesPersonId(Integer customerId, Integer carId, Integer salesPersonId) {
        SalesInvoice salesInvoice = salesInvoiceRepository.lookForSalesInvoiceByCustomerIdAndCarIdAndSalesPersonId(customerId, carId, salesPersonId);

        if(salesInvoice == null) {
            throw new ResourceNotFoundException("sales invoice");
        }

        return salesInvoice;
    }

    public SalesInvoice lookForSalesInvoiceByCustomerIdAndCarId(Integer customerId, Integer carId) {
        SalesInvoice salesInvoice = salesInvoiceRepository.lookForSalesInvoiceByCustomerIdAndCarId(customerId, carId);

        if(salesInvoice == null) {
            throw new ResourceNotFoundException("sales invoice");
        }

        return salesInvoice;
    }

    public HashMap<String, Object> addSalesInvoice(SalesInvoice salesInvoice, String username, String password) throws SimpleException {
        // 1. validate the sales person credentials.
        // only sales person who can create invoices.
        SalesPerson salesPerson = salesPersonService.login(username, password);

        if(salesPerson == null) {
            throw new SimpleException("username or password is invalid.");
        }

        // 2. make sure car exists
        carService.carExists(salesInvoice.getCarId());

        // 3. make sure that there's no registered invoice with the provided car id
        if(salesInvoiceRepository.lookForSalesByCarId(salesInvoice.getCarId()) != null) {
            throw new SimpleException("you cannot create invoice for this car because there's a registered invoice with the provided car id.");
        }

        // 4. make sure customer exists
        customerService.customerExists(salesInvoice.getCustomerId());

        // 5. calculate the sales person bonus
        // car sub price * 5% = bonus
        salesInvoice.setSalesPersonBonus(salesInvoice.getSubPrice() * salesInvoice.getSalesPersonBonus());
        salesInvoice.setSalespersonId(salesPerson.getId());
        salesInvoice.setInvoiceUUID(UUID.randomUUID().toString());

        // 6. create invoice
        SalesInvoice salesInvoice1 = salesInvoiceRepository.save(salesInvoice);

        System.out.println(salesInvoice1.getCreatedAt());

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the sales invoice have been created.");
        response.put("salesInvoice", salesInvoice1);

        return response;
    }

    // I know there are duplicate logic everywhere
    // and I could extract it to another method
    // however there's a reason behind it.
    public HashMap<String, Object> updateSalesInvoiceByInvoiceUUID(String invoiceUUID, UpdateSalesInvoiceDTO updateSalesInvoiceDTO, String username, String password) throws ResourceNotFoundException, SimpleException {
        // make sure the sales person who provided their credentials
        // is the one who actually created the invoice.
        SalesPerson salesPerson = salesPersonService.login(username, password);

        if(salesPerson == null) {
            throw new SimpleException("username or password is invalid.");
        }

        // make sure car exists.
        carService.carExists(updateSalesInvoiceDTO.getCarId());

        // make sure that there's no registered invoice with the provided car id
        if(salesInvoiceRepository.lookForSalesByCarId(updateSalesInvoiceDTO.getCarId()) != null) {
            throw new SimpleException("you cannot create invoice for this car because there's a registered invoice with the provided car id.");
        }

        SalesInvoice salesInvoice = salesInvoiceRepository.findSalesInvoiceByInvoiceUUID(invoiceUUID);

        if(salesInvoice == null) {
            throw new ResourceNotFoundException("sales invoice");
        }

        if(!Objects.equals(salesInvoice.getSalespersonId(), salesPerson.getId())) {
            throw new SimpleException("you don't have permissions to update this invoice. this is not your invoice.");
        }



        salesInvoice.setCarId(updateSalesInvoiceDTO.getCarId());
        salesInvoice.setSubPrice(updateSalesInvoiceDTO.getSubPrice());
        salesInvoice.setType(updateSalesInvoiceDTO.getType());
        salesInvoice.setInstalmentPerMonth(updateSalesInvoiceDTO.getInstalmentPerMonth());

        salesInvoiceRepository.save(salesInvoice);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the sales invoice have been updated.");
        response.put("salesInvoice", salesInvoice);

        return response;
    }

    // I know there are duplicate logic everywhere
    // and I could extract it to another method
    // however there's a reason behind it.
    public HashMap<String, Object> updateSalesInvoiceById(Integer id, UpdateSalesInvoiceDTO updateSalesInvoiceDTO, String username, String password) throws ResourceNotFoundException, SimpleException {
        // make sure the sales person who provided their credentials
        // is the one who actually created the invoice.
        SalesPerson salesPerson = salesPersonService.login(username, password);

        if(salesPerson == null) {
            throw new SimpleException("username or password is invalid.");
        }

        // make sure car exists.
        carService.carExists(updateSalesInvoiceDTO.getCarId());

        // make sure that there's no registered invoice with the provided car id
        if(salesInvoiceRepository.lookForSalesByCarId(updateSalesInvoiceDTO.getCarId()) != null) {
            throw new SimpleException("you cannot create invoice for this car because there's a registered invoice with the provided car id.");
        }

        SalesInvoice salesInvoice = salesInvoiceRepository.findSalesInvoiceById(id);

        if(salesInvoice == null) {
            throw new ResourceNotFoundException("sales invoice");
        }

        if(!Objects.equals(salesInvoice.getSalespersonId(), salesPerson.getId())) {
            throw new SimpleException("you don't have permissions to update this invoice. this is not your invoice.");
        }


        salesInvoice.setCarId(updateSalesInvoiceDTO.getCarId());
        salesInvoice.setSubPrice(updateSalesInvoiceDTO.getSubPrice());
        salesInvoice.setType(updateSalesInvoiceDTO.getType());
        salesInvoice.setInstalmentPerMonth(updateSalesInvoiceDTO.getInstalmentPerMonth());

        salesInvoiceRepository.save(salesInvoice);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the sales invoice have been updated.");
        response.put("salesInvoice", salesInvoice);

        return response;
    }


    // I know there are duplicate logic everywhere
    // and I could extract it to another method
    // however there's a reason behind it.
    public HashMap<String, Object> deleteSalesInvoice(Integer id, String username, String password) throws ResourceNotFoundException {

        // make sure the sales person who provided their credentials
        // is the one who actually created the invoice.
        SalesPerson salesPerson = salesPersonService.login(username, password);

        if(salesPerson == null) {
            throw new SimpleException("username or password is invalid.");
        }

        SalesInvoice salesInvoice = salesInvoiceRepository.findSalesInvoiceById(id);

        if(salesInvoice == null) {
            throw new ResourceNotFoundException("sales invoice");
        }

        if(!Objects.equals(salesInvoice.getSalespersonId(), salesPerson.getId())) {
            throw new SimpleException("you don't have permissions to delete this invoice. this is not your invoice.");
        }

        salesInvoiceRepository.deleteById(id);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the sales invoice have been deleted.");
        response.put("salesInvoice", salesInvoice);

        return response;
    }

}
