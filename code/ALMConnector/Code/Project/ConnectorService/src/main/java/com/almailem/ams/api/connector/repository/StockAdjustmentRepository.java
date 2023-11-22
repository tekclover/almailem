package com.almailem.ams.api.connector.repository;

import com.almailem.ams.api.connector.model.stockadjustment.StockAdjustment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface StockAdjustmentRepository extends JpaRepository<StockAdjustment, String> {

}
