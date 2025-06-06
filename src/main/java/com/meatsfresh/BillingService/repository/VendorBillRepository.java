package com.meatsfresh.BillingService.repository;

import com.meatsfresh.BillingService.entity.VendorBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VendorBillRepository extends JpaRepository<VendorBill, String> {
    List<VendorBill> getBillsByVendorId(Long vendorId);
}
