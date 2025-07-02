package com.meatsfresh.BillingService.service;

import com.meatsfresh.BillingService.config.ServiceUrlProperties;
import com.meatsfresh.BillingService.dto.AgentDTO;
import com.meatsfresh.BillingService.dto.OrderAgentSummeryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AgentOrderAggregatorService {

    private final WebClient.Builder webClientBuilder;

    private final ServiceUrlProperties serviceUrlProperties;

    public List<AgentDTO> getAllAgents() {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrlProperties.getAgentBaseUrl())
                .retrieve()
                .bodyToFlux(AgentDTO.class)
                .collectList()
                .block();
    }

    public List<OrderAgentSummeryDTO> getOrdersByAgentWithDateRange(Long agentId, LocalDate start, LocalDate end){

        String url = UriComponentsBuilder.fromUriString(serviceUrlProperties.getOrderBaseUrl())
                .path("/agent/{agentId}/filter")
                .queryParam("start", start)
                .queryParam("end", end)
                .buildAndExpand(agentId)
                .toUriString();

        return webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToFlux(OrderAgentSummeryDTO.class)
                .collectList()
                .block();
    }
}
