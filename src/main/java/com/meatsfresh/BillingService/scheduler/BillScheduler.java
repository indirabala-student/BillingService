package com.meatsfresh.BillingService.scheduler;

import com.meatsfresh.BillingService.entity.AgentBill;
import com.meatsfresh.BillingService.entity.VendorBill;
import com.meatsfresh.BillingService.service.AgentBillService;
import com.meatsfresh.BillingService.service.VendorBillService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BillScheduler {

    private final VendorBillService vendorBillService;

    private final AgentBillService agentBillService;

    // Every Wednesday at 1:00 AM
//    @Scheduled(cron = "0 0 1 * * WED")
    @Scheduled(cron = "0 * * * * *")
    public void autoGenerateVendorBills() {

        // Define date range: last Wednesday to this Wednesday
        LocalDate today = LocalDate.now();
        LocalDate lastWednesday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.WEDNESDAY));
        LocalDate thisWednesday = lastWednesday.plusDays(6); // Week ends on next Tuesday 11:59 PM

        List<VendorBill> vendorBills = vendorBillService.saveAllVendorBills(lastWednesday, thisWednesday);
        List<AgentBill> agentBills= agentBillService.saveAllAgentBills(lastWednesday, thisWednesday);
    }
}
