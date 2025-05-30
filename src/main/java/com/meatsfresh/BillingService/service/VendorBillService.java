package com.meatsfresh.BillingService.service;

import com.meatsfresh.BillingService.entity.VendorBill;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface VendorBillService {

    public VendorBill generateVendorBill(String vendorId, List<String> orderIds, double totalOrderValue, double commissionRate, LocalDateTime fromDate, LocalDateTime toDate);

    public List<VendorBill> getBillsByVendorId(String vendorId);

    public VendorBill getBillById(String billId);

    VendorBill markAsPaid(String billId);

}
