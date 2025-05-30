package com.meatsfresh.BillingService.service.impl;

import com.meatsfresh.BillingService.entity.BillStatus;
import com.meatsfresh.BillingService.entity.VendorBill;
import com.meatsfresh.BillingService.repository.VendorBillRepository;
import com.meatsfresh.BillingService.service.VendorBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VendorBillServiceImpl implements VendorBillService {

    private final VendorBillRepository vendorBillRepository;

    @Autowired
    public VendorBillServiceImpl(VendorBillRepository vendorBillRepository){
        this.vendorBillRepository=vendorBillRepository;
    }

    @Override
    public VendorBill generateVendorBill(String vendorId, List<String> orderIds, double totalOrderValue, double commissionRate, LocalDateTime fromDate, LocalDateTime toDate) {
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
    public List<VendorBill> getBillsByVendorId(String vendorId) {
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
}
