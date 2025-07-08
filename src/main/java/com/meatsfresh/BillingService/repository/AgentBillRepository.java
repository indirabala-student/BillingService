package com.meatsfresh.BillingService.repository;

import com.meatsfresh.BillingService.entity.AgentBill;
import com.meatsfresh.BillingService.entity.VendorBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AgentBillRepository extends JpaRepository<AgentBill, String> {
    List<AgentBill> getBillsByAgentId(Long agentId);


    @Query("SELECT b FROM AgentBill b WHERE b.agentId = :agentId AND (" +
            "(:fromDate BETWEEN b.fromDate AND b.toDate) OR " +
            "(:toDate BETWEEN b.fromDate AND b.toDate) OR " +
            "(b.fromDate BETWEEN :fromDate AND :toDate) OR " +
            "(b.toDate BETWEEN :fromDate AND :toDate))")
    List<AgentBill> findOverlappingBillsByAgent(@Param("agentId") Long agentId,
                                                  @Param("fromDate") LocalDateTime startDate,
                                                  @Param("toDate") LocalDateTime endDate);
}
