package com.almailem.ams.api.connector.service;

import com.almailem.ams.api.connector.model.perpetual.PerpetualHeader;
import com.almailem.ams.api.connector.repository.PerpetualHeaderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PerpetualService {

    @Autowired
    PerpetualHeaderRepository perpetualHeaderRepo;

    /**
     * Get All StockCount Perpetual Details
     *
     * @return
     */
    public List<PerpetualHeader> getAllPerpetualDetails() {
        List<PerpetualHeader> perpetuals = perpetualHeaderRepo.findAll();
        return perpetuals;
    }
}
