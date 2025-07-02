package com.meatsfresh.BillingService.repository;

import com.meatsfresh.BillingService.entity.AgentBill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgentBillRepository extends JpaRepository<AgentBill, String> {
    List<AgentBill> getBillsByAgentId(Long agentId);
}
