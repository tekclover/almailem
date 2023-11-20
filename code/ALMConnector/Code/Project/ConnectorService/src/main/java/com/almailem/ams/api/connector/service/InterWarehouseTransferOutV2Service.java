package com.almailem.ams.api.connector.service;

import com.almailem.ams.api.connector.model.transferout.TransferOutHeader;
import com.almailem.ams.api.connector.repository.TransferOutHeaderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class InterWarehouseTransferOutV2Service {

    @Autowired
    TransferOutHeaderRepository transferOutHeaderRepo;

    /**
     * Get All InterWarehouseTransferOutV2 Details
     *
     * @return
     */
    public List<TransferOutHeader> getAllInterWhTransferOutV2Details() {
        List<TransferOutHeader> transferOuts = transferOutHeaderRepo.findAll();
        return transferOuts;
    }
}
