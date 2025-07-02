package com.meatsfresh.BillingService.dto;

import java.util.List;

public class AgentOrderDTO {

    private AgentDTO agent;
    private List<OrderAgentSummeryDTO> orders;

    public AgentDTO getAgent() {
        return agent;
    }

    public void setAgent(AgentDTO agent) {
        this.agent = agent;
    }

    public List<OrderAgentSummeryDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderAgentSummeryDTO> orders) {
        this.orders = orders;
    }
}
