package com.cp2396f09_sem4_grp3.onmart_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cp2396f09_sem4_grp3.onmart_common.entities.ExportStockInvoiceDetails;

@Repository
public interface ExportStockInvoiceDetailsRepository extends JpaRepository<ExportStockInvoiceDetails, Long> {

}
