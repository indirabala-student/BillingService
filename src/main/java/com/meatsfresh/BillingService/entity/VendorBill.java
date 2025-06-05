package com.meatsfresh.BillingService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorBill {

    @Id
    private String billId;

    private Long vendorId;

    @ElementCollection
    @CollectionTable(name = "billing_order_ids", joinColumns = @JoinColumn(name = "billing_id"))
    @Column(name = "order_id")
    private List<Long> orderIds;

    private double totalOrderValue;

    private double totalCommission;

    private double vendorPayment;

    private LocalDateTime fromDate;

    private LocalDateTime toDate;

    private BillStatus status;

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public List<Long> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(List<Long> orderIds) {
        this.orderIds = orderIds;
    }

    public double getTotalOrderValue() {
        return totalOrderValue;
    }

    public void setTotalOrderValue(double totalOrderValue) {
        this.totalOrderValue = totalOrderValue;
    }

    public double getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(double totalCommission) {
        this.totalCommission = totalCommission;
    }

    public double getVendorPayment() {
        return vendorPayment;
    }

    public void setVendorPayment(double vendorPayment) {
        this.vendorPayment = vendorPayment;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateTime toDate) {
        this.toDate = toDate;
    }

    public BillStatus getStatus() {
        return status;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
    }
}
