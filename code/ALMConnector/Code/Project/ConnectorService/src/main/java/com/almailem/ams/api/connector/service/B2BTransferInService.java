package com.almailem.ams.api.connector.service;

import com.almailem.ams.api.connector.model.transferin.TransferInHeader;
import com.almailem.ams.api.connector.repository.TransferInHeaderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class B2BTransferInService {

    @Autowired
    TransferInHeaderRepository transferInHeaderRepo;

    /**
     * Get All B2BTransferIn Details
     *
     * @return
     */
    public List<TransferInHeader> getAllB2BTransferInDetails() {
        List<TransferInHeader> headerRepoAll = transferInHeaderRepo.findAll();
        return headerRepoAll;
    }
}
