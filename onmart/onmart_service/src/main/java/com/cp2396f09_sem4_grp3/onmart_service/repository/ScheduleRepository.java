package com.cp2396f09_sem4_grp3.onmart_service.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cp2396f09_sem4_grp3.onmart_common.entities.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
        @Query("SELECT s FROM Schedule s WHERE s.date BETWEEN :fromDate AND :toDate")
        Page<Schedule> findSchedulesWithinDateRange(@Param("fromDate") LocalDate fromDate,
                        @Param("toDate") LocalDate toDate, Pageable pageable);

        @Query("SELECT s FROM Schedule s JOIN s.scheduleDetails sd WHERE s.date BETWEEN :fromDate AND :toDate AND sd.employeeId = :employeeId")
        Page<Schedule> findSchedulesWithinDateRangeAndAccountId(@Param("fromDate") LocalDate fromDate,
                        @Param("toDate") LocalDate toDate, @Param("employeeId") Long employeeId, Pageable pageable);

        @Query("SELECT s FROM Schedule s WHERE s.date = :date")
        Optional<Schedule> findSchedulesByDate(@Param("date") LocalDate date);

        @Query("SELECT s FROM Schedule s WHERE s.date BETWEEN :fromDate AND :toDate")
        List<Schedule> findSchedulesBetweenDatetome(@Param("fromDate") LocalDate fromDate,
                        @Param("toDate") LocalDate toDate);

}
