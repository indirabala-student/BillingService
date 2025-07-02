package com.meatsfresh.BillingService.util;

import com.meatsfresh.BillingService.constants.Constants;
import com.meatsfresh.BillingService.dto.AgentDTO;
import com.meatsfresh.BillingService.dto.OrderAgentSummeryDTO;
import com.meatsfresh.BillingService.dto.OrderVendorSummaryDTO;
import com.meatsfresh.BillingService.dto.VendorDTO;
import com.meatsfresh.BillingService.entity.AgentBill;
import com.meatsfresh.BillingService.entity.BillStatus;
import com.meatsfresh.BillingService.entity.VendorBill;
import com.meatsfresh.BillingService.service.AgentOrderAggregatorService;
import com.meatsfresh.BillingService.service.VendorOrderAggregatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AgentBillUtil {

    private final AgentOrderAggregatorService aggregatorService;

    public AgentBill generateAgentBill(Long agentId, LocalDate start, LocalDate end){
        List<OrderAgentSummeryDTO> orders=aggregatorService.getOrdersByAgentWithDateRange(agentId,start,end);
        if (orders == null || orders.isEmpty()) {
            return null;
        }

        List<Long> orderIds = orders.stream()
                .map(OrderAgentSummeryDTO::getOrderId)
                .toList();

        double totalOrderValue = orders.stream()
                .mapToDouble(OrderAgentSummeryDTO::getOrderValue)
                .sum();

        double comFromShop = Constants.AGENT_COMMISSION_FROM_SHOP;
        double comToAgent=Constants.AGENT_COMMISSION_TO_SHOP;
        double totalCommission = (totalOrderValue * comFromShop) + (totalOrderValue * comToAgent);
        double agentPayment = totalOrderValue - totalCommission;

        return AgentBill.builder()
                .billId(UUID.randomUUID().toString())
                .agentId(agentId)
                .orderIds(orderIds)
                .totalOrderValue(totalOrderValue)
                .totalCommission(totalCommission)
                .agentPayment(agentPayment)
                .fromDate(start.atStartOfDay())
                .toDate(end.atStartOfDay())
                .status(BillStatus.GENERATED)
                .build();
    }

    public List<AgentBill> generateBills(LocalDate start, LocalDate end){
        List<AgentDTO> agents=aggregatorService.getAllAgents();
        List<AgentBill> bills=new ArrayList<>();
        for (AgentDTO agent:agents){
            AgentBill bill = generateAgentBill(agent.getAgentId(), start, end);
            if (bill != null) {
                bills.add(bill);
            }
        }
        return bills;
    }
}
