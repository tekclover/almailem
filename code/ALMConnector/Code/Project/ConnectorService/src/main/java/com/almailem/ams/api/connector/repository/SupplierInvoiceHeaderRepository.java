package com.almailem.ams.api.connector.repository;

import com.almailem.ams.api.connector.model.supplierinvoice.SupplierInvoiceHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface SupplierInvoiceHeaderRepository extends JpaRepository<SupplierInvoiceHeader, String> {

    List<SupplierInvoiceHeader> findTopByProcessedStatusIdOrderByOrderReceivedOn(long l);

    SupplierInvoiceHeader findBySupplierInvoiceNo(String asnNumber);

    SupplierInvoiceHeader findTopBySupplierInvoiceNoOrderByOrderReceivedOnDesc(String asnNumber);
}
