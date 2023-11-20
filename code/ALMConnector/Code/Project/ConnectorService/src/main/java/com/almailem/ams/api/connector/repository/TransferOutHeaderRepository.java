package com.almailem.ams.api.connector.repository;


import com.almailem.ams.api.connector.model.transferout.TransferOutHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TransferOutHeaderRepository extends JpaRepository<TransferOutHeader, String>,
        JpaSpecificationExecutor<TransferOutHeader> {

}
