package com.almailem.ams.api.connector.repository;

import com.almailem.ams.api.connector.model.purchasereturn.PurchaseReturnHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PurchaseReturnHeaderRepository extends JpaRepository<PurchaseReturnHeader, String>,
        JpaSpecificationExecutor<PurchaseReturnHeader> {

}