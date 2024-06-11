package com.cp2396f09_sem4_grp3.onmart_service.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ScheduleRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ScheduleResponse;

public interface ScheduleService {
    Page<ScheduleResponse> getScheduleFromDateToDate(Integer page, LocalDate fromDate, LocalDate toDate);

    Page<ScheduleResponse> getScheduleFromDateToDateByAccount(Integer page, LocalDate fromDate, LocalDate toDate,
            String email);

    ScheduleResponse createSchedule(ScheduleRequest request, String email);

    void deleteSchedule(Long id);
}
