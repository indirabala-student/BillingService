package com.meatsfresh.BillingService.service;

import com.meatsfresh.BillingService.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderClient {

    @Value("${order.service.url}")
    private String orderServiceUrl;

    private final RestTemplate restTemplate;

    public OrderClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<OrderDTO> getOrdersByVendorAndDate(String vendorId, LocalDateTime from, LocalDateTime to) {
        String url = String.format("%s?vendorId=%s&fromDate=%s&toDate=%s",
                orderServiceUrl, vendorId, from.toString(), to.toString());

        ResponseEntity<OrderDTO[]> response = restTemplate.getForEntity(url, OrderDTO[].class);
        return Arrays.asList(response.getBody());
    }
}

