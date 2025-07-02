package com.meatsfresh.BillingService.dto;

public class AgentDTO {

    private long agentId;
    private String name;

    public long getAgentId() {
        return agentId;
    }

    public void setAgentId(double agentId) {
        this.agentId = (long) agentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
