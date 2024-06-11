package com.cp2396f09_sem4_grp3.onmart_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.order.OrderDashboardResponse;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.schedule.ScheduleDashboardResponse;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.user.UserDashboardResponse;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.warehouse.WarehouseDashboardResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.DashboardService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/users")
    public UserDashboardResponse getUserDashboard(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(defaultValue = "asc") String order) {
        if (year == null) {
            year = getNowYear(year);
        }
        if (month == null) {
            month = getNowMonth(month);
        }

        return dashboardService.getUsersDashboardBetweenDatetime(year, month, order);
    }

    @GetMapping("/orders")
    public OrderDashboardResponse getOrderDashboard(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(defaultValue = "asc") String order) {
        if (year == null) {
            year = getNowYear(year);
        }
        if (month == null) {
            month = getNowMonth(month);
        }
        return dashboardService.getOrderDashboardBetweenDatetime(year, month, order);
    }

    @GetMapping("/warehouse")
    public WarehouseDashboardResponse getWarehouseDashboard(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "10", name = "low-stock") Integer lowStock) {
        if (year == null) {
            year = getNowYear(year);
        }
        if (month == null) {
            month = getNowMonth(month);
        }
        return dashboardService.getWarehouseDashboardBetweenDatetime(year, month, order, lowStock);
    }

    @GetMapping("/schedule")
    public ScheduleDashboardResponse getScheduleDashboard(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "3") Integer top) {
        if (year == null) {
            year = getNowYear(year);
        }
        if (month == null) {
            month = getNowMonth(month);
        }
        return dashboardService.getScheduleDashboardBetweenDatetime(year, month, order, top);
    }

    private Integer getNowMonth(Integer month) {
        // Get current date
        LocalDate now = LocalDate.now();

        // Use current month if not provided
        return (month != null) ? month : now.getMonthValue();
    }

    private Integer getNowYear(Integer year) {
        // Get current date
        LocalDate now = LocalDate.now();

        // Use current year if not provided
        return (year != null) ? year : now.getYear();
    }

}
