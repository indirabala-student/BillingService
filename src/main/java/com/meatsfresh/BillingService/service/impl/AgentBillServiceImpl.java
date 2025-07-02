package com.meatsfresh.BillingService.service.impl;

import com.meatsfresh.BillingService.entity.AgentBill;
import com.meatsfresh.BillingService.entity.BillStatus;
import com.meatsfresh.BillingService.repository.AgentBillRepository;
import com.meatsfresh.BillingService.service.AgentBillService;
import com.meatsfresh.BillingService.service.AgentOrderAggregatorService;
import com.meatsfresh.BillingService.util.AgentBillUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgentBillServiceImpl implements AgentBillService {

    private final AgentBillRepository agentBillRepository;

    private final AgentBillUtil billUtil;

    private final AgentOrderAggregatorService aggregatorService;

    @Override
    public List<AgentBill> getBillsByAgentId(Long agentId) {
        return agentBillRepository.getBillsByAgentId(agentId);
    }

    @Override
    public AgentBill getBillById(String billId) {
        return agentBillRepository.findById(billId).orElse(null);
    }

    @Override
    public AgentBill markAsPaid(String billId) {
        AgentBill bill=agentBillRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found with ID: " + billId));

        bill.setStatus(BillStatus.PAID);
        return agentBillRepository.save(bill);
    }

    @Override
    public AgentBill saveAgentBill(Long agentId, LocalDate start, LocalDate end) {
        return agentBillRepository.save(billUtil.generateAgentBill(agentId,start,end));
    }

    @Override
    public List<AgentBill> saveAllAgentBills(LocalDate start, LocalDate end) {
        List<AgentBill> bills = billUtil.generateBills(start, end)
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return agentBillRepository.saveAll(bills);
    }
}
