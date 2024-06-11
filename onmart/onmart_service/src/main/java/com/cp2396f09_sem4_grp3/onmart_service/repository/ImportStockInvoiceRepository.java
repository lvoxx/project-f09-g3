package com.cp2396f09_sem4_grp3.onmart_service.repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cp2396f09_sem4_grp3.onmart_common.entities.ImportStockInvoice;

@Repository
public interface ImportStockInvoiceRepository extends JpaRepository<ImportStockInvoice, Long> {
        @Query("SELECT i FROM ImportStockInvoice i WHERE (i.createdOn BETWEEN :start AND :end) ORDER BY i.id ASC")
        List<ImportStockInvoice> findImportByCreatedOnBetweenSortedAsc(
                        @Param("start") ZonedDateTime start,
                        @Param("end") ZonedDateTime end);

        @Query("SELECT i FROM ImportStockInvoice i WHERE (i.createdOn BETWEEN :start AND :end) ORDER BY i.id DESC")
        List<ImportStockInvoice> findImportByCreatedOnBetweenSortedDesc(
                        @Param("start") ZonedDateTime start,
                        @Param("end") ZonedDateTime end);

        // Count imports in month
        @Query("SELECT COUNT(i) FROM ImportStockInvoice i WHERE i.createdOn BETWEEN :start AND :end")
        Long countByBetweenDate(
                        @Param("start") ZonedDateTime start,
                        @Param("end") ZonedDateTime end);

        // Sum Products import in month
        @Query("SELECT SUM(imd.quantity) FROM ImportStockInvoice i JOIN i.importStockInvoiceDetails imd WHERE i.createdOn BETWEEN :start AND :end")
        Long sumProductsByBetweenDate(
                        @Param("start") ZonedDateTime start,
                        @Param("end") ZonedDateTime end);
}
