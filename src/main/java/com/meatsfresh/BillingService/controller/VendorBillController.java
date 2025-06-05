package com.meatsfresh.BillingService.controller;
import com.meatsfresh.BillingService.dto.OrderDTO;
import com.meatsfresh.BillingService.dto.VendorDTO;
import com.meatsfresh.BillingService.dto.VendorOrderDTO;
import com.meatsfresh.BillingService.entity.VendorBill;
import com.meatsfresh.BillingService.service.VendorBillService;
import com.meatsfresh.BillingService.service.VendorOrderAggregatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/billing")
@RequiredArgsConstructor
public class VendorBillController {

    private final VendorBillService vendorBillService;

    private final VendorOrderAggregatorService aggregatorService;

    @PostMapping("/generate/vendor/{vendorId}")
    public ResponseEntity<VendorBill> generateVendorBill(
            @PathVariable Long vendorId,
            @RequestBody Map<String, Object> payload
            ){

        // Sample payload fields: orderIds, totalOrderValue, commissionRate, fromDate, toDate
        List<Long> orderIds = (List<Long>) payload.get("orderIds");
        double totalOrderValue = Double.parseDouble(payload.get("totalOrderValue").toString());
        double commissionRate = Double.parseDouble(payload.get("commissionRate").toString());
        LocalDateTime fromDate = LocalDateTime.parse(payload.get("fromDate").toString());
        LocalDateTime toDate = LocalDateTime.parse(payload.get("toDate").toString());

        VendorBill bill = vendorBillService.generateVendorBill(vendorId, orderIds, totalOrderValue, commissionRate, fromDate, toDate);
        return ResponseEntity.ok(bill);
    }

    @PostMapping("/save/vendor/{vendorId}")
    public ResponseEntity<VendorBill> saveVendorBill(@PathVariable Long vendorId,@RequestParam LocalDate start, @RequestParam LocalDate end){

        VendorBill bill=vendorBillService.saveVendorBill(vendorId,start,end);
        return ResponseEntity.ok(bill);
    }

    @PostMapping("/saveAll")
    public ResponseEntity<List<VendorBill>> saveBills(@RequestParam LocalDate start, @RequestParam LocalDate end){
        List<VendorBill> vendorBills=vendorBillService.generateVendorBills(start,end);
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

    @GetMapping("/vendors")
    public List<VendorDTO> getAllVendors(){
        return aggregatorService.getAllVendors();
    }


    @PostMapping("/vendor-orders")
    public List<VendorOrderDTO> getVendorsOrders(@RequestBody List<VendorDTO> vendors) {
        return aggregatorService.getVendorOrders(vendors);
    }

    @GetMapping("/vendor/{vendorId}/orders")
    public List<OrderDTO> getVendorOrders(@PathVariable Long vendorId){
        return aggregatorService.getOrdersByVendorId(vendorId);
    }

    @GetMapping("/all-vendors-orders")
    public List<VendorOrderDTO> getAllVendorsOrders(){
        List<VendorDTO> vendors = aggregatorService.getAllVendors();
        return aggregatorService.getVendorOrders(vendors);
    }

    @GetMapping("/all-vendors-orders/filter")
    public List<VendorOrderDTO> getAllVendorsOrdersWithDateRange(@RequestParam LocalDate start, @RequestParam LocalDate end){
        List<VendorDTO> vendors=aggregatorService.getAllVendors();
        return aggregatorService.getVendorOrdersWithDateRange(vendors,start,end);
    }

    @GetMapping("/vendor/{vendorId}/orders/filter")
    public List<OrderDTO> getVendorOrdersWithDateRange(@PathVariable Long vendorId,@RequestParam LocalDate start, @RequestParam LocalDate end){
        return aggregatorService.getOrdersByVendorWithDateRange(vendorId, start,end);
    }

}
