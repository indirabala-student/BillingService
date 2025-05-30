package com.meatsfresh.BillingService.service;

import com.meatsfresh.BillingService.dto.OrderDTO;
import com.meatsfresh.BillingService.dto.VendorDTO;
import com.meatsfresh.BillingService.dto.VendorOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BillingAggregatorService {

    private final VendorClient vendorClient;
    private final OrderClient orderClient;

    @Autowired
    public BillingAggregatorService(VendorClient vendorClient, OrderClient orderClient) {
        this.vendorClient = vendorClient;
        this.orderClient = orderClient;
    }

    public List<VendorOrderDTO> getVendorOrders(LocalDateTime from, LocalDateTime to) {
        List<VendorDTO> vendors = vendorClient.getAllVendors();

        List<VendorOrderDTO> result = new ArrayList<>();

        for (VendorDTO vendor : vendors) {
            List<OrderDTO> orders = orderClient.getOrdersByVendorAndDate(vendor.getId(), from, to);
            VendorOrderDTO vo = new VendorOrderDTO();
            vo.setVendor(vendor);
            vo.setOrders(orders);
            result.add(vo);
        }

        return result;
    }
}
