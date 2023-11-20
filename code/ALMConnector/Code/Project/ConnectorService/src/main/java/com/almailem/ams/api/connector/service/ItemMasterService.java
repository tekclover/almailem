package com.almailem.ams.api.connector.service;

import com.almailem.ams.api.connector.model.master.ItemMaster;
import com.almailem.ams.api.connector.repository.ItemMasterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ItemMasterService {

    @Autowired
    ItemMasterRepository itemMasterRepo;

    /**
     * Get All ItemMaster Details
     *
     * @return
     */
    public List<ItemMaster> getAllItemMasterDetails() {
        List<ItemMaster> imList = itemMasterRepo.findAll();
        return imList;
    }
}
