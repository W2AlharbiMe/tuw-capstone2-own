package com.example.capstone2.Service;

import com.example.capstone2.Api.Exception.ResourceNotFoundException;
import com.example.capstone2.Api.Exception.SimpleException;
import com.example.capstone2.DTO.UpdateSalesInvoiceDTO;
import com.example.capstone2.Model.*;
import com.example.capstone2.Repository.InventoryItemRepository;
import com.example.capstone2.Repository.SalesInvoiceRepository;
import com.example.capstone2.Repository.SerialNumberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SalesInvoiceService {

    private final SalesInvoiceRepository salesInvoiceRepository;
    private final CarService carService;
    private final CustomerService customerService;
    private final SalesPersonService salesPersonService;
    private final InventoryItemRepository inventoryItemRepository;
    private final SerialNumberRepository serialNumberRepository;


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
        Car car = carService.findCarById(serialNumber1.getCarId());
        Customer customer = customerService.findById(salesInvoice.getCustomerId());
        SalesPerson salesPerson = salesPersonService.findById(salesInvoice.getSalesPersonId());

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

    public HashMap<String, Object> addSalesInvoice(SalesInvoice salesInvoice, String username, String password) throws SimpleException {
        SalesPerson salesPerson = salesPersonLogin(username, password);

        carService.carExists(salesInvoice.getCarId());

        InventoryItem inventoryItem = getInventoryItemByCarId(salesInvoice.getCarId());

        customerService.customerExists(salesInvoice.getCustomerId());

        // car sub price * 5% = bonus
        salesInvoice.setSalesPersonBonus(salesInvoice.getSubPrice() * salesInvoice.getSalesPersonBonus());
        salesInvoice.setSalesPersonId(salesPerson.getId());
        salesInvoice.setInvoiceUUID(UUID.randomUUID().toString());

        SalesInvoice salesInvoice1 = salesInvoiceRepository.save(salesInvoice);

        SerialNumber serialNumber = serialNumberRepository.latestUnusedSerialNumberByCarId(salesInvoice.getCarId());
        serialNumber.setUsed(true);
        serialNumberRepository.save(serialNumber);

        Car car = carService.findCarById(salesInvoice.getCarId());
        Customer customer = customerService.findById(salesInvoice.getCustomerId());

        inventoryItemOperation(false, inventoryItem);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the sales invoice have been created.");
        response.put("salesInvoice", salesInvoice1);
        response.put("car", car);
        response.put("customer", customer);
        response.put("serialNumber", serialNumber);
        response.put("salesPerson", salesPerson);

        return response;
    }

    public HashMap<String, Object> updateInvoice(Integer id, String UUID, UpdateSalesInvoiceDTO updateSalesInvoiceDTO, String username, String password, String field) throws ResourceNotFoundException, SimpleException {
        // make sure invoice exists.
        SalesInvoice saved_salesInvoice = field.equalsIgnoreCase("id") ? findById(id) : findByInvoiceUUID(UUID);

        // make sure the status is not paid.
        if(saved_salesInvoice.getStatus().equalsIgnoreCase("paid")) {
            throw new SimpleException("you can not update a paid invoice.");
        }

        // make sure sales person credentials are correct
        SalesPerson salesPerson = salesPersonLogin(username, password);

        // make sure car exists.
        carService.carExists(updateSalesInvoiceDTO.getCarId());

        // make sure sales person is the same who created this invoice.
        if(!Objects.equals(saved_salesInvoice.getSalesPersonId(), salesPerson.getId())) {
            throw new SimpleException("you don't have permissions to update this invoice. this is not your invoice.");
        }

        // make sure there's inventory and quantity of the car
        InventoryItem inventoryItem = getInventoryItemByCarId(updateSalesInvoiceDTO.getCarId());
        InventoryItem old_inventoryItem = getInventoryItemByCarId(saved_salesInvoice.getCarId());

        inventoryItemOperation(true, old_inventoryItem);
        inventoryItemOperation(false, inventoryItem);

        SerialNumber serialNumber = serialNumberRepository.findSerialNumberById(saved_salesInvoice.getSerialNumberId());
        serialNumber.setUsed(false);
        serialNumberRepository.save(serialNumber);

        saved_salesInvoice.setCarId(updateSalesInvoiceDTO.getCarId());
        saved_salesInvoice.setSubPrice(updateSalesInvoiceDTO.getSubPrice());
        saved_salesInvoice.setType(updateSalesInvoiceDTO.getType());
        saved_salesInvoice.setInstalmentPerMonth(updateSalesInvoiceDTO.getInstalmentPerMonth());

        salesInvoiceRepository.save(saved_salesInvoice);

        SerialNumber serialNumber1 = serialNumberRepository.latestUnusedSerialNumberByCarId(saved_salesInvoice.getCarId());
        serialNumber1.setUsed(true);
        serialNumberRepository.save(serialNumber1);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the sales invoice have been updated.");
        response.put("salesInvoice", saved_salesInvoice);
        response.put("car", carService.findCarById(saved_salesInvoice.getCarId()));
        response.put("salesPerson", salesPerson);
        response.put("inventoryItem", inventoryItem); // this is the new target car inventory item
        response.put("serialNumber", serialNumber1); // this is the new target car inventory item


        return response;
    }


    private SalesPerson salesPersonLogin(String username, String password) throws SimpleException {
        SalesPerson salesPerson = salesPersonService.login(username, password);

        if(salesPerson == null) {
            throw new SimpleException("username or password is invalid.");
        }

        return salesPerson;
    }

    private InventoryItem getInventoryItemByCarId(Integer carId) throws ResourceNotFoundException {
        InventoryItem inventoryItem = inventoryItemRepository.findByItemIdAndType(carId, "car");

        if(inventoryItem == null) {
            throw new ResourceNotFoundException("inventory item");
        }

        if(inventoryItem.getQuantity() == 0) {
            throw new SimpleException("car out of stock.");
        }

        return inventoryItem;
    }

    public HashMap<String, Object> deleteSalesInvoice(Integer id, String username, String password) throws ResourceNotFoundException {
        // make sure the sales person who provided their credentials
        // is the one who actually created the invoice.
        SalesPerson salesPerson = salesPersonLogin(username, password);

        SalesInvoice salesInvoice = findById(id);

        if(!Objects.equals(salesInvoice.getSalesPersonId(), salesPerson.getId())) {
            throw new SimpleException("you don't have permissions to delete this invoice. this is not your invoice.");
        }

        salesInvoiceRepository.deleteById(id);

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "the sales invoice have been deleted.");
        response.put("salesInvoice", salesInvoice);

        return response;
    }

    private void inventoryItemOperation(Boolean increment, InventoryItem inventoryItem) {
        Integer quantity = inventoryItem.getQuantity();
        inventoryItem.setQuantity(increment ? quantity + 1 : quantity  - 1);
        inventoryItemRepository.save(inventoryItem);
    }

}
