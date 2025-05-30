package com.meatsfresh.BillingService.scheduler;

import com.meatsfresh.BillingService.dto.VendorOrderDTO;
import com.meatsfresh.BillingService.entity.VendorBill;
import com.meatsfresh.BillingService.service.BillingAggregatorService;
import com.meatsfresh.BillingService.service.VendorBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Component
public class VendorBillScheduler {

    @Autowired
    private BillingAggregatorService billingAggregatorService;

    @Autowired
    private VendorBillService vendorBillService;

    @Scheduled(cron = "0 0 0 * * WED") // Every Wednesday at 12 AM
    public void generateWeeklyBills() {
        double commissionRate = 0.1; // Make this configurable if needed

        // Get current date and time
        LocalDateTime now = LocalDateTime.now();

        // Calculate last Wednesday at 00:00
        LocalDate today = now.toLocalDate();
        LocalDate currentWednesday = today.with(DayOfWeek.WEDNESDAY);
        if (today.getDayOfWeek() != DayOfWeek.WEDNESDAY) {
            currentWednesday = today.with(TemporalAdjusters.previous(DayOfWeek.WEDNESDAY));
        }

        LocalDate lastWednesday = currentWednesday.minusWeeks(1);

        LocalDateTime fromDate = lastWednesday.atStartOfDay();
        LocalDateTime toDate = currentWednesday.atStartOfDay().withHour(23).withMinute(59).withSecond(59);

        System.out.println("📅 Generating bills from: " + fromDate + " to " + toDate);

        List<VendorOrderDTO> vendorOrdersList = billingAggregatorService.getVendorOrders(fromDate, toDate);
        List<VendorBill> generatedBills = new ArrayList<>();

        for (VendorOrderDTO vendorOrders : vendorOrdersList) {
            String vendorId = vendorOrders.getVendor().getId();
            List<String> orderIds = vendorOrders.getOrders().stream()
                    .map(order -> order.getOrderId())
                    .toList();

            double totalOrderValue = vendorOrders.getOrders().stream()
                    .mapToDouble(order -> order.getOrderValue())
                    .sum();

            VendorBill bill = vendorBillService.generateVendorBill(
                    vendorId, orderIds, totalOrderValue, commissionRate, fromDate, toDate
            );

            generatedBills.add(bill);
        }

        System.out.println("✅ Weekly vendor bills generated: " + generatedBills.size());
    }
}
