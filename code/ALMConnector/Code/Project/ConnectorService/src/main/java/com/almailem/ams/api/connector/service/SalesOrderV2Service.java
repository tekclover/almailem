package com.almailem.ams.api.connector.service;

import com.almailem.ams.api.connector.model.picklist.PickListHeader;
import com.almailem.ams.api.connector.repository.PickListHeaderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SalesOrderV2Service {

    @Autowired
    PickListHeaderRepository pickListHeaderRepo;

    /**
     * Get All PickList Details
     *
     * @return
     */
    public List<PickListHeader> getAllSalesOrderV2Details() {
        List<PickListHeader> pickLists = pickListHeaderRepo.findAll();
        return pickLists;
    }
}
