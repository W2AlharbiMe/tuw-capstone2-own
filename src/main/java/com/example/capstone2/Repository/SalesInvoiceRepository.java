package com.example.capstone2.Repository;

import com.example.capstone2.Model.SalesInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesInvoiceRepository extends JpaRepository<SalesInvoice, Integer> {

    SalesInvoice findSalesInvoiceById(Integer id);

    SalesInvoice findSalesInvoiceByInvoiceUUID(String invoiceUUID);

    List<SalesInvoice> findSalesInvoicesByCustomerId(Integer customerId);

    List<SalesInvoice> findSalesInvoiceByType(String type);

    SalesInvoice findSalesInvoiceBySerialNumberId(Integer serialNumber);

    @Query("SELECT s FROM sales_invoices s WHERE s.car.id = ?1")
    SalesInvoice lookForSalesByCarId(Integer carId);

    @Query("SELECT s FROM sales_invoices s WHERE s.salesperson.id = ?1")
    List<SalesInvoice> lookForSalesInvoicesBySalesPersonId(Integer salespersonId);

    @Query("SELECT s FROM sales_invoices s WHERE s.customer.id = ?1 AND s.car.id = ?2 AND s.salesperson.id = ?3 ORDER BY s.id ASC LIMIT 1")
    SalesInvoice lookForSalesInvoiceByCustomerIdAndCarIdAndSalesPersonId(Integer customerId, Integer carId, Integer salesPersonId);

    @Query("SELECT s FROM sales_invoices s WHERE s.customer.id = ?1 ORDER BY s.id ASC LIMIT 1")
    SalesInvoice atCustomerHaveLeastOneInvoice(Integer customerId);


    @Query("SELECT s FROM sales_invoices s WHERE s.customer.id = ?1 AND s.car.id = ?2 ORDER BY s.id ASC LIMIT 1")
    SalesInvoice lookForSalesInvoiceByCustomerIdAndCarId(Integer customerId, Integer carId);

    @Query("SELECT s FROM sales_invoices s WHERE s.salesperson.id = ?1 ORDER BY s.id ASC LIMIT 1")
    SalesInvoice atLeastOneSalesBySalesPersonId(Integer salesPersonId);
}
