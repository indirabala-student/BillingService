package com.meatsfresh.BillingService.service.impl;

import com.meatsfresh.BillingService.constants.Constants;
import com.meatsfresh.BillingService.dto.OrderDTO;
import com.meatsfresh.BillingService.dto.VendorDTO;
import com.meatsfresh.BillingService.entity.BillStatus;
import com.meatsfresh.BillingService.entity.VendorBill;
import com.meatsfresh.BillingService.repository.VendorBillRepository;
import com.meatsfresh.BillingService.service.VendorBillService;
import com.meatsfresh.BillingService.service.VendorOrderAggregatorService;
import com.meatsfresh.BillingService.util.VendorBillUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendorBillServiceImpl implements VendorBillService {

    private final VendorBillRepository vendorBillRepository;

    private final VendorBillUtil billUtil;

    private final VendorOrderAggregatorService aggregatorService;

    @Override
    public VendorBill generateVendorBill(Long vendorId, List<Long> orderIds, double totalOrderValue, double commissionRate, LocalDateTime fromDate, LocalDateTime toDate) {
        double totalCommission=totalOrderValue*commissionRate;
        double vendorPayout=totalOrderValue-totalCommission;

        VendorBill bill=new VendorBill();
        bill.setBillId(UUID.randomUUID().toString());
        bill.setVendorId(vendorId);
        bill.setOrderIds(orderIds);
        bill.setTotalOrderValue(totalOrderValue);
        bill.setTotalCommission(totalCommission);
        bill.setVendorPayment(vendorPayout);
        bill.setStatus(BillStatus.GENERATED);
        bill.setFromDate(fromDate);
        bill.setToDate(toDate);

        return vendorBillRepository.save(bill);
    }

    @Override
    public List<VendorBill> getBillsByVendorId(Long vendorId) {
        return vendorBillRepository.getBillsByVendorId(vendorId);
    }

    @Override
    public VendorBill getBillById(String billId) {
        return vendorBillRepository.findById(billId).orElse(null);
    }

    @Override
    public VendorBill markAsPaid(String billId) {
        VendorBill bill = vendorBillRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found with ID: " + billId));

        bill.setStatus(BillStatus.PAID);
        return vendorBillRepository.save(bill);
    }

    @Override
    public VendorBill saveVendorBill(Long vendorId, LocalDate start, LocalDate end) {
        return vendorBillRepository.save(billUtil.generateVendorBill(vendorId,start,end));
    }

    @Override
    public List<VendorBill> generateVendorBills(LocalDate start, LocalDate end) {
        List<VendorBill> bills = billUtil.generateBills(start, end)
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return vendorBillRepository.saveAll(bills);
    }
}
