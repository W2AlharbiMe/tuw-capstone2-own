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

    @Query("SELECT s FROM sales_invoices s WHERE s.carId = ?1")
    SalesInvoice lookForSalesByCarId(Integer carId);

    @Query("SELECT s FROM sales_invoices s WHERE s.salespersonId = ?1")
    List<SalesInvoice> lookForSalesInvoicesBySalesPersonId(Integer salespersonId);

    @Query("SELECT s FROM sales_invoices s WHERE s.customerId = ?1 AND s.carId = ?2 AND s.salespersonId = ?3 ORDER BY s.id ASC LIMIT 1")
    SalesInvoice lookForSalesInvoiceByCustomerIdAndCarIdAndSalesPersonId(Integer customerId, Integer carId, Integer salesPersonId);

    @Query("SELECT s FROM sales_invoices s WHERE s.customerId = ?1 ORDER BY s.id ASC LIMIT 1")
    SalesInvoice atCustomerHaveLeastOneInvoice(Integer customerId);


    @Query("SELECT s FROM sales_invoices s WHERE s.customerId = ?1 AND s.carId = ?2 ORDER BY s.id ASC LIMIT 1")
    SalesInvoice lookForSalesInvoiceByCustomerIdAndCarId(Integer customerId, Integer carId);

    @Query("SELECT s FROM sales_invoices s WHERE s.salespersonId = ?1 ORDER BY s.id ASC LIMIT 1")
    SalesInvoice atLeastOneSalesBySalesPersonId(Integer salesPersonId);
}
