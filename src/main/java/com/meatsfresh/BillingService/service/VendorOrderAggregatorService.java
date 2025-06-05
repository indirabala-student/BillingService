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

//    @Value("vendor.service.url")
    private static final String VENDOR_SERVICE_BASE_URL="http://localhost:8081/api/vendors";

    private static final String ORDER_SERVICE_BASE_URL = "http://localhost:8080/api/orders"; // Change as needed


    public List<VendorDTO> getAllVendors() {
        return webClientBuilder.build()
                .get()
                .uri(VENDOR_SERVICE_BASE_URL)
                .retrieve()
                .bodyToFlux(VendorDTO.class)
                .collectList()
                .block(); // blocking to keep it simple; you can go fully reactive if needed
    }

    public List<VendorOrderDTO> getVendorOrders(List<VendorDTO> vendors) {
        return vendors.stream()
                .map(vendor -> {
                    List<OrderDTO> orders = getOrdersByVendorId(vendor.getVendorId());
                    return new VendorOrderDTO(vendor, orders);
                })
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getOrdersByVendorId(Long vendorId) {
        return webClientBuilder.build()
                .get()
                .uri(ORDER_SERVICE_BASE_URL + "/vendor/" + vendorId)
                .retrieve()
                .bodyToFlux(OrderDTO.class)
                .collectList()
                .block();
    }

    public List<VendorOrderDTO> getVendorOrdersWithDateRange(List<VendorDTO> vendors, LocalDate start, LocalDate end) {
        return vendors.stream()
                .map(vendor -> {
                    List<OrderDTO> orders = getOrdersByVendorWithDateRange(vendor.getVendorId(), start, end);
                    return new VendorOrderDTO(vendor, orders);
                })
                .collect(Collectors.toList());
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
