package com.meatsfresh.BillingService.service;

import com.meatsfresh.BillingService.config.ServiceUrlProperties;
import com.meatsfresh.BillingService.dto.OrderDTO;
import com.meatsfresh.BillingService.dto.VendorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorOrderAggregatorService {

    private final WebClient.Builder webClientBuilder;

    private final ServiceUrlProperties serviceUrlProperties;

    public List<VendorDTO> getAllVendors() {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrlProperties.getVendorBaseUrl())
                .retrieve()
                .bodyToFlux(VendorDTO.class)
                .collectList()
                .block();
    }

    public List<OrderDTO> getOrdersByVendorWithDateRange(Long vendorId, LocalDate start, LocalDate end){

        String url = UriComponentsBuilder.fromUriString(serviceUrlProperties.getOrderBaseUrl())
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
