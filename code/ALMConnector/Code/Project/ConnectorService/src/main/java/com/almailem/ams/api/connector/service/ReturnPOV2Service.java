package com.almailem.ams.api.connector.service;

import com.almailem.ams.api.connector.model.purchasereturn.PurchaseReturnHeader;
import com.almailem.ams.api.connector.repository.PurchaseReturnHeaderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ReturnPOV2Service {

    @Autowired
    PurchaseReturnHeaderRepository purchaseReturnHeaderRepo;

    /**
     * Get All ReturnPOV2 Details
     *
     * @return
     */
    public List<PurchaseReturnHeader> getAllReturnPoV2Details() {
        List<PurchaseReturnHeader> purchaseReturns = purchaseReturnHeaderRepo.findAll();
        return purchaseReturns;
    }
}
