package com.meatsfresh.BillingService.controller;
import com.meatsfresh.BillingService.entity.VendorBill;
import com.meatsfresh.BillingService.service.VendorBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/billing")
public class VendorBillController {

    private final VendorBillService vendorBillService;

    @Autowired
    public VendorBillController(VendorBillService vendorBillService) {
        this.vendorBillService = vendorBillService;
    }

    @PostMapping("/generate/vendor/{vendorId}")
    public ResponseEntity<VendorBill> generateVendorBill(
            @PathVariable String vendorId,
            @RequestBody Map<String, Object> payload
            ){

        // Sample payload fields: orderIds, totalOrderValue, commissionRate, fromDate, toDate
        List<String> orderIds = (List<String>) payload.get("orderIds");
        double totalOrderValue = Double.parseDouble(payload.get("totalOrderValue").toString());
        double commissionRate = Double.parseDouble(payload.get("commissionRate").toString());
        LocalDateTime fromDate = LocalDateTime.parse(payload.get("fromDate").toString());
        LocalDateTime toDate = LocalDateTime.parse(payload.get("toDate").toString());

        VendorBill bill = vendorBillService.generateVendorBill(vendorId, orderIds, totalOrderValue, commissionRate, fromDate, toDate);
        return ResponseEntity.ok(bill);
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<VendorBill>> getBillsByVendorId(
            @PathVariable String vendorId){
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
