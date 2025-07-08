package com.meatsfresh.BillingService.controller;
import com.meatsfresh.BillingService.entity.VendorBill;
import com.meatsfresh.BillingService.exception.DateRangeConflictException;
import com.meatsfresh.BillingService.service.VendorBillService;
import com.meatsfresh.BillingService.service.VendorOrderAggregatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/vendor-billing")
@RequiredArgsConstructor
public class VendorBillController {

    private final VendorBillService vendorBillService;

    private final VendorOrderAggregatorService aggregatorService;

    @PostMapping("/save/vendor/{vendorId}")
    public ResponseEntity<VendorBill> saveVendorBill(@PathVariable Long vendorId,@RequestParam LocalDate start, @RequestParam LocalDate end){
            VendorBill bill=vendorBillService.saveVendorBill(vendorId,start,end);
            return ResponseEntity.ok(bill);
    }

    @PostMapping("/saveAll")
    public ResponseEntity<List<VendorBill>> saveBills(@RequestParam LocalDate start, @RequestParam LocalDate end){
        List<VendorBill> vendorBills=vendorBillService.saveAllVendorBills(start,end);
        return ResponseEntity.ok(vendorBills);
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<VendorBill>> getBillsByVendorId(
            @PathVariable Long vendorId){
        return ResponseEntity.ok(vendorBillService.getBillsByVendorId(vendorId));
    }

    @GetMapping("/{billId}")
    public ResponseEntity<VendorBill> getBillByBillId(
            @PathVariable String billId){
        VendorBill bill=vendorBillService.getBillById(billId);
        if(bill==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(bill);
    }

    @PutMapping("/mark-paid/{billId}")
    public ResponseEntity<VendorBill> markBillAsPaid(@PathVariable String billId) {
        VendorBill updatedBill = vendorBillService.markAsPaid(billId);
        return ResponseEntity.ok(updatedBill);
    }
}
