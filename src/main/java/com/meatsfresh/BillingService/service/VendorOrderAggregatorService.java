package com.meatsfresh.BillingService.service;

import com.meatsfresh.BillingService.dto.OrderDTO;
import com.meatsfresh.BillingService.dto.VendorDTO;
import com.meatsfresh.BillingService.dto.VendorOrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendorOrderAggregatorService {

    private final WebClient.Builder webClientBuilder;

    private static final String VENDOR_SERVICE_BASE_URL="http://localhost:8081/api/vendors";

    private static final String ORDER_SERVICE_BASE_URL = "http://localhost:8080/api/orders";

    public List<VendorDTO> getAllVendors() {
        return webClientBuilder.build()
                .get()
                .uri(VENDOR_SERVICE_BASE_URL)
                .retrieve()
                .bodyToFlux(VendorDTO.class)
                .collectList()
                .block();
    }

    public List<OrderDTO> getOrdersByVendorWithDateRange(Long vendorId, LocalDate start, LocalDate end){

        String url = UriComponentsBuilder
                .fromHttpUrl(ORDER_SERVICE_BASE_URL)
                .path("/vendor/{vendorId}/filter")
                .queryParam("start", start)
                .queryParam("end", end)
                .buildAndExpand(vendorId)
                .toUriString();

        return webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToFlux(OrderDTO.class)
                .collectList()
                .block();
    }
}
