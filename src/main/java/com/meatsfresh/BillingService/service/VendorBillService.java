package com.meatsfresh.BillingService.service;

import com.meatsfresh.BillingService.entity.VendorBill;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public interface VendorBillService {

    public VendorBill generateVendorBill(Long vendorId, List<Long> orderIds, double totalOrderValue, double commissionRate, LocalDateTime fromDate, LocalDateTime toDate);

    public List<VendorBill> getBillsByVendorId(Long vendorId);

    public VendorBill getBillById(String billId);

    VendorBill markAsPaid(String billId);

    VendorBill saveVendorBill(Long vendorId, LocalDate start, LocalDate end);

    List<VendorBill> generateVendorBills(LocalDate start, LocalDate end);
}
