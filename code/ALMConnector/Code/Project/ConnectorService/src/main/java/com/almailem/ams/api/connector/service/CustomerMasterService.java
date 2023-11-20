package com.almailem.ams.api.connector.service;

import com.almailem.ams.api.connector.model.master.CustomerMaster;
import com.almailem.ams.api.connector.repository.CustomerMasterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomerMasterService {

    @Autowired
    CustomerMasterRepository customerMasterRepo;

    /**
     * Get All CustomerMaster Details
     *
     * @return
     */
    public List<CustomerMaster> getAllCustomerMasterDetails() {
        List<CustomerMaster> customerMasters = customerMasterRepo.findAll();
        return customerMasters;
    }
}
