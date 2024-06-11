package com.cp2396f09_sem4_grp3.onmart_service.repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cp2396f09_sem4_grp3.onmart_common.entities.ExportStockInvoice;

@Repository
public interface ExportStockInvoiceRepository extends JpaRepository<ExportStockInvoice, Long> {
        @Query("SELECT e FROM ExportStockInvoice e WHERE (e.createdOn BETWEEN :start AND :end) ORDER BY e.id ASC")
        List<ExportStockInvoice> findExportByCreatedOnBetweenSortedAsc(
                        @Param("start") ZonedDateTime start,
                        @Param("end") ZonedDateTime end);

        @Query("SELECT e FROM ExportStockInvoice e WHERE (e.createdOn BETWEEN :start AND :end) ORDER BY e.id DESC")
        List<ExportStockInvoice> findExportByCreatedOnBetweenSortedDesc(
                        @Param("start") ZonedDateTime start,
                        @Param("end") ZonedDateTime end);

        // Count exports in month
        @Query("SELECT COUNT(e) FROM ExportStockInvoice e WHERE e.createdOn BETWEEN :start AND :end")
        Long countByBetweenDate(
                        @Param("start") ZonedDateTime start,
                        @Param("end") ZonedDateTime end);

        // Sum Products export in month
        @Query("SELECT SUM(ed.quantity) FROM ExportStockInvoice e JOIN e.exportStockInvoiceDetails ed WHERE e.createdOn BETWEEN :start AND :end")
        Long sumProductsByBetweenDate(
                        @Param("start") ZonedDateTime start,
                        @Param("end") ZonedDateTime end);

}
