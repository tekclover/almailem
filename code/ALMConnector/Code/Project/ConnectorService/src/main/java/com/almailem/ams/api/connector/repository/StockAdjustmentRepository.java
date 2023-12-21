package com.almailem.ams.api.connector.repository;

import com.almailem.ams.api.connector.model.stockadjustment.StockAdjustment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface StockAdjustmentRepository extends JpaRepository<StockAdjustment, String>, JpaSpecificationExecutor<StockAdjustment> {

    List<StockAdjustment> findTopByProcessedStatusIdOrderByOrderReceivedOn(long l);

    StockAdjustment findTopByItemCodeOrderByOrderReceivedOnDesc(String itemCode);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE STOCKADJUSTMENT set processedStatusId = :processedStatusId, orderProcessedOn = getdate() \r\n"
            + " WHERE stockAdjustmentId = :stockAdjustmentId ", nativeQuery = true)
    void updateProcessStatusId(@Param(value = "stockAdjustmentId") Long stockAdjustmentId,
                               @Param(value = "processedStatusId") Long processedStatusId);

    StockAdjustment findTopByStockAdjustmentIdAndItemCodeAndProcessedStatusIdOrderByOrderReceivedOnDesc(Long stockAdjustmentId, String itemCode, Long processStatusId);
}