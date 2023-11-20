package com.almailem.ams.api.connector.service;

import com.almailem.ams.api.connector.model.stockAdjustment.StockAdjustment;
import com.almailem.ams.api.connector.repository.StockAdjustmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StockAdjustmentService {

    @Autowired
    StockAdjustmentRepository stockAdjustmentRepository;

    public List<StockAdjustment> getAllStockAdjustment(){
        List<StockAdjustment> stockAdjustments = stockAdjustmentRepository.findAll();
        return stockAdjustments;
    }
}
