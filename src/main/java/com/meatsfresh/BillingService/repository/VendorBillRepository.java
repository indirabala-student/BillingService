package com.meatsfresh.BillingService.repository;

import com.meatsfresh.BillingService.entity.VendorBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VendorBillRepository extends JpaRepository<VendorBill, String> {
    List<VendorBill> getBillsByVendorId(Long vendorId);

    @Query("SELECT b FROM VendorBill b WHERE b.vendorId = :vendorId AND (" +
            "(:fromDate BETWEEN b.fromDate AND b.toDate) OR " +
            "(:toDate BETWEEN b.fromDate AND b.toDate) OR " +
            "(b.fromDate BETWEEN :fromDate AND :toDate) OR " +
            "(b.toDate BETWEEN :fromDate AND :toDate))")
    List<VendorBill> findOverlappingBillsByVendor(@Param("vendorId") Long vendorId,
                                                  @Param("fromDate") LocalDateTime startDate,
                                                  @Param("toDate") LocalDateTime endDate);

}
