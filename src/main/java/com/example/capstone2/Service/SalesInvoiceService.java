package com.example.capstone2.Service;

import com.example.capstone2.Api.Exception.ResourceNotFoundException;
import com.example.capstone2.Api.Exception.SimpleException;
import com.example.capstone2.DTO.SalesInvoiceDTO;
import com.example.capstone2.Model.*;
import com.example.capstone2.Repository.InventoryItemRepository;
import com.example.capstone2.Repository.SalesInvoiceRepository;
import com.example.capstone2.Repository.SalesPersonRepository;
import com.example.capstone2.Repository.SerialNumberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SalesInvoiceService {

    private final SalesInvoiceRepository salesInvoiceRepository;
    private final CarService carService;
    private final CustomerService customerService;
    private final SalesPersonService salesPersonService;
    private final InventoryItemRepository inventoryItemRepository;
    private final SerialNumberRepository serialNumberRepository;
    private final SalesPersonRepository salesPersonRepository;



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

    public HashMap<String, Object> findBySerialNumber(String serialNumber) {
        SerialNumber serialNumber1 = serialNumberRepository.findCarBySerialNumber(serialNumber);

        if(serialNumber1 == null) {
            throw new ResourceNotFoundException("serial number");
        }

        SalesInvoice salesInvoice = salesInvoiceRepository.findSalesInvoiceBySerialNumberId(serialNumber1.getId());
        Car car = carService.findCarById(serialNumber1.getCar().getId());
        Customer customer = customerService.findById(salesInvoice.getCustomer().getId());
        SalesPerson salesPerson = salesPersonService.findById(salesInvoice.getSalesperson().getId());

        HashMap<String, Object> response = new HashMap<>();
        response.put("salesInvoice", salesInvoice);
        response.put("car", car);
        response.put("customer", customer);
        response.put("serialNumber", serialNumber1);
        response.put("salesPerson", salesPerson);


        return response;
    }

    public List<SalesInvoice> findByType(String type) throws ResourceNotFoundException, SimpleException {
        String[] validTypes = {"full_car_payment", "instalment_car_payment", "full_service_payment", "instalment_service_payment"};

        if(Arrays.stream(validTypes).noneMatch(t -> t.equalsIgnoreCase(type))) {
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

    public HashMap<String, Object> payInvoice(Integer id) {
        SalesInvoice salesInvoice = findById(id);

        if(salesInvoice.getStatus().equalsIgnoreCase("paid")) {
            throw new SimpleException("this invoice is already paid.");
        }

        salesInvoice.setStatus("paid");
        salesInvoiceRepository.save(salesInvoice);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the sales invoice status have been changed to paid.");
        response.put("salesInvoice", salesInvoice);

        return response;
    }

    public HashMap<String, Object> addSalesInvoice(SalesInvoiceDTO salesInvoiceDTO, User user) throws SimpleException {
        carService.carExists(salesInvoiceDTO.getCarId());

        InventoryItem inventoryItem = getInventoryItemByCarId(salesInvoiceDTO.getCarId());

        customerService.customerExists(salesInvoiceDTO.getCustomerId());

        // car sub price * 5% = bonus
        SalesInvoice salesInvoice = new SalesInvoice();
        SalesPerson salesPerson = salesPersonRepository.findSalesPersonById(user.getId());

        salesInvoice.setSalesPersonBonus(salesInvoice.getSubPrice() * salesInvoice.getSalesPersonBonus());
        salesInvoice.setSalesperson(salesPerson);
        salesInvoice.setInvoiceUUID(UUID.randomUUID().toString());

        SerialNumber serialNumber = serialNumberRepository.latestUnusedSerialNumberByCarId(salesInvoice.getCar().getId());
        serialNumber.setIsUsed(true);
        serialNumberRepository.save(serialNumber);


        SalesInvoice salesInvoice1 = salesInvoiceRepository.save(salesInvoice);


        Car car = carService.findCarById(salesInvoice.getCar().getId());
        Customer customer = customerService.findById(salesInvoice.getCustomer().getId());

        inventoryItemOperation(false, inventoryItem);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the sales invoice have been created.");
        response.put("salesInvoice", salesInvoice1);
        response.put("car", car);
        response.put("customer", customer);
//        response.put("serialNumber", serialNumber);
        response.put("salesPerson", salesPerson);

        return response;
    }


    private InventoryItem getInventoryItemByCarId(Integer carId) throws ResourceNotFoundException {
        List<InventoryItem> inventoryItem = inventoryItemRepository.findInventoryItemsByCarId(carId);

        if(inventoryItem.isEmpty()) {
            throw new ResourceNotFoundException("inventory item");
        }

        if(inventoryItem.getQuantity() == 0) {
            throw new SimpleException("car out of stock.");
        }

        return inventoryItem;
    }

    private void inventoryItemOperation(Boolean increment, InventoryItem inventoryItem) {
        Integer quantity = inventoryItem.getQuantity();
        inventoryItem.setQuantity(increment ? quantity + 1 : quantity  - 1);
        inventoryItemRepository.save(inventoryItem);
    }

}
