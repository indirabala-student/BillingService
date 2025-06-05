package com.meatsfresh.BillingService.repository;

import com.meatsfresh.BillingService.entity.VendorBill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorBillRepository extends JpaRepository<VendorBill, String> {
    List<VendorBill> getBillsByVendorId(Long vendorId);
}
