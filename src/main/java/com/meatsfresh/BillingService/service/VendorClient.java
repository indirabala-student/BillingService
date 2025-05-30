package com.meatsfresh.BillingService.service;

import com.meatsfresh.BillingService.dto.VendorDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class VendorClient {

    @Value("${vendor.service.url}")
    private String vendorServiceUrl;

    private final RestTemplate restTemplate;

    public VendorClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<VendorDTO> getAllVendors() {
        ResponseEntity<VendorDTO[]> response = restTemplate.getForEntity(vendorServiceUrl, VendorDTO[].class);
        return Arrays.asList(response.getBody());
    }
}
