package com.meatsfresh.BillingService.util;

import com.meatsfresh.BillingService.constants.Constants;
import com.meatsfresh.BillingService.dto.OrderDTO;
import com.meatsfresh.BillingService.dto.VendorDTO;
import com.meatsfresh.BillingService.entity.BillStatus;
import com.meatsfresh.BillingService.entity.VendorBill;
import com.meatsfresh.BillingService.service.VendorOrderAggregatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class VendorBillUtil {

    private final VendorOrderAggregatorService aggregatorService;

    public VendorBill generateVendorBill(Long vendorId, LocalDate start, LocalDate end){
        List<OrderDTO> orders=aggregatorService.getOrdersByVendorWithDateRange(vendorId,start,end);
        if (orders == null || orders.isEmpty()) {
            return null;
        }

        List<Long> orderIds = orders.stream()
                .map(OrderDTO::getOrderId)
                .toList();

        double totalOrderValue = orders.stream()
                .mapToDouble(OrderDTO::getOrderValue)
                .sum();

        double commissionRate = Constants.VENDOR_COMMISSION_FOR_PLATFORM;
        double totalCommission = totalOrderValue * commissionRate;
        double vendorPayment = totalOrderValue - totalCommission;

        return VendorBill.builder()
                .billId(UUID.randomUUID().toString())
                .vendorId(vendorId)
                .orderIds(orderIds)
                .totalOrderValue(totalOrderValue)
                .totalCommission(totalCommission)
                .vendorPayment(vendorPayment)
                .fromDate(start.atStartOfDay())
                .toDate(end.atStartOfDay())
                .status(BillStatus.GENERATED)
                .build();
    }

    public List<VendorBill> generateBills(LocalDate start, LocalDate end){
        List<VendorDTO> vendors=aggregatorService.getAllVendors();
        List<VendorBill> vendorBills=new ArrayList<>();
        for (VendorDTO vendor:vendors){
            VendorBill bill = generateVendorBill(vendor.getVendorId(), start, end);
            if (bill != null) {
                vendorBills.add(bill);
            }
        }
        return vendorBills;
    }
}
