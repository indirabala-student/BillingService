package com.meatsfresh.BillingService.dto;

import java.util.List;

public class VendorOrderDTO {
    private VendorDTO vendor;
    private List<OrderDTO> orders;

    public VendorDTO getVendor() {
        return vendor;
    }

    public void setVendor(VendorDTO vendor) {
        this.vendor = vendor;
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }
}
