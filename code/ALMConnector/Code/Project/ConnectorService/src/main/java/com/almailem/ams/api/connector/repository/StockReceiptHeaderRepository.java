package com.almailem.ams.api.connector.repository;

import com.almailem.ams.api.connector.model.stockreceipt.StockReceiptHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface StockReceiptHeaderRepository extends JpaRepository<StockReceiptHeader, String> {

    StockReceiptHeader findByReceiptNo(String asnNumber);

    List<StockReceiptHeader> findTopByProcessedStatusIdOrderByOrderReceivedOn(long l);
}
