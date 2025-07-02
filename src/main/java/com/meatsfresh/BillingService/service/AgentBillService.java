package com.meatsfresh.BillingService.service;

import com.meatsfresh.BillingService.entity.AgentBill;
import com.meatsfresh.BillingService.entity.VendorBill;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface AgentBillService {

    public List<AgentBill> getBillsByAgentId(Long agentId);

    public AgentBill getBillById(String billId);

    AgentBill markAsPaid(String billId);

    AgentBill saveAgentBill(Long agentId, LocalDate start, LocalDate end);

    List<AgentBill> saveAllAgentBills(LocalDate start, LocalDate end);
}
