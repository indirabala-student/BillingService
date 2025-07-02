package com.meatsfresh.BillingService.dto;

import lombok.AllArgsConstructor;

import java.util.List;


@AllArgsConstructor
public class VendorOrderDTO {

    private VendorDTO vendor;
    private List<OrderVendorSummaryDTO> orders;

    public VendorDTO getVendor() {
        return vendor;
    }

    public void setVendor(VendorDTO vendor) {
        this.vendor = vendor;
    }

    public List<OrderVendorSummaryDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderVendorSummaryDTO> orders) {
        this.orders = orders;
    }
}
