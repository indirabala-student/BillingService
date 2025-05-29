package com.meatsfresh.BillingService.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class VendorBill {

    @Id
    private String billId;

    private String vendorId;

    @ElementCollection
    @CollectionTable(name = "billing_order_ids", joinColumns = @JoinColumn(name = "billing_id"))
    @Column(name = "order_id")
    private List<String> orderIds;

    private double totalOrderValue;

    private double totalCommission;

    private double vendorPayment;

    private LocalDateTime fromDate;

    private LocalDateTime toDate;

    private BillStatus status;
}
