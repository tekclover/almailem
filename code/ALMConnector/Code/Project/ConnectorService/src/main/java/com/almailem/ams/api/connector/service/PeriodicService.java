package com.almailem.ams.api.connector.service;

import com.almailem.ams.api.connector.model.periodic.PeriodicHeader;
import com.almailem.ams.api.connector.repository.PeriodicHeaderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PeriodicService {

    @Autowired
    PeriodicHeaderRepository periodicHeaderRepo;

    /**
     * Get All StockCount Periodic Details
     *
     * @return
     */
    public List<PeriodicHeader> getAllPeriodicDetails() {
        List<PeriodicHeader> periodic = periodicHeaderRepo.findAll();
        return periodic;
    }
}
