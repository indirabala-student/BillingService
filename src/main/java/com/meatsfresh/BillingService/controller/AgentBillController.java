package com.meatsfresh.BillingService.controller;

import com.meatsfresh.BillingService.entity.AgentBill;
import com.meatsfresh.BillingService.service.AgentBillService;
import com.meatsfresh.BillingService.service.VendorOrderAggregatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/agent-billing")
@RequiredArgsConstructor
public class AgentBillController {

    private final AgentBillService agentBillService;

    private final VendorOrderAggregatorService aggregatorService;

    @PostMapping("/save/agent/{agentId}")
    public ResponseEntity<AgentBill> saveAgentBill(@PathVariable Long agentId, @RequestParam LocalDate start, @RequestParam LocalDate end){

        AgentBill bill= agentBillService.saveAgentBill(agentId,start,end);
        return ResponseEntity.ok(bill);
    }

    /**
     * ------ generate and save all vendor bills -------
     *  exposed to only admin (or)
     *  Only scheduler can hit this endpoint
     *  no one else can use this endpoint
     */
    @PostMapping("/saveAll")
    public ResponseEntity<List<AgentBill>> saveBills(@RequestParam LocalDate start, @RequestParam LocalDate end){
        List<AgentBill> bills= agentBillService.saveAllAgentBills(start,end);
        return ResponseEntity.ok(bills);
    }

    @GetMapping("/agent/{agentId}")
    public ResponseEntity<List<AgentBill>> getBillsByVendorId(
            @PathVariable Long agentId){
        return ResponseEntity.ok(agentBillService.getBillsByAgentId(agentId));
    }

    @GetMapping("/{billId}")
    public ResponseEntity<AgentBill> getBillByBillId(
            @PathVariable String billId){
        AgentBill bill= agentBillService.getBillById(billId);
        if(bill==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(bill);
    }

    @PutMapping("/mark-paid/{billId}")
    public ResponseEntity<AgentBill> markBillAsPaid(@PathVariable String billId) {
        AgentBill updatedBill = agentBillService.markAsPaid(billId);
        return ResponseEntity.ok(updatedBill);
    }
}
