package com.almailem.ams.api.connector.repository;

import com.almailem.ams.api.connector.model.salesreturn.SalesReturnLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesReturnLineRepository extends JpaRepository<SalesReturnLine, String> {
}
