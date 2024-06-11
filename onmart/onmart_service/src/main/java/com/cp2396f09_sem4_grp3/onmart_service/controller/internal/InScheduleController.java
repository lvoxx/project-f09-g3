package com.cp2396f09_sem4_grp3.onmart_service.controller.internal;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ScheduleRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ScheduleResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.ScheduleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/private/schedules")
@RequiredArgsConstructor
@Slf4j
public class InScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ScheduleResponse> getScheduleFromDateToDate(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate) {
        if (fromDate == null) {
            fromDate = LocalDate.now().minus(3, ChronoUnit.DAYS);
        }

        if (toDate == null) {
            toDate = LocalDate.now().plus(6, ChronoUnit.DAYS);
        }
        log.info("From: " + fromDate.toString());
        log.info("To: " + toDate.toString());
        return scheduleService.getScheduleFromDateToDate(page, fromDate, toDate);
    }

    @GetMapping("/account")
    @ResponseStatus(HttpStatus.OK)
    public Page<ScheduleResponse> getScheduleFromDateToDateAndAccount(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate,
            @AuthenticationPrincipal UserDetails user) {
        if (fromDate == null) {
            fromDate = LocalDate.now().minus(3, ChronoUnit.DAYS);
        }

        if (toDate == null) {
            toDate = LocalDate.now().plus(6, ChronoUnit.DAYS);
        }
        return scheduleService.getScheduleFromDateToDateByAccount(page, fromDate, toDate, user.getUsername());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduleResponse createSchedule(@RequestBody ScheduleRequest request,
            @AuthenticationPrincipal UserDetails user) {
        return scheduleService.createSchedule(request, user.getUsername());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
    }

}
