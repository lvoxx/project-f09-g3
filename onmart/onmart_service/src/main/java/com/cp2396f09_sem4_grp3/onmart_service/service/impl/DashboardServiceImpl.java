package com.cp2396f09_sem4_grp3.onmart_service.service.impl;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.order.OrderDashboardResponse;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.schedule.EmployeeShiftCount;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.schedule.ScheduleDashboardResponse;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.schedule.ScheduleDataByDay;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.user.UserDashboardResponse;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.warehouse.WarehouseDashboardResponse;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.warehouse.WarehouseWarning;
import com.cp2396f09_sem4_grp3.onmart_common.entities.ExportStockInvoice;
import com.cp2396f09_sem4_grp3.onmart_common.entities.ImportStockInvoice;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Order;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Product;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Schedule;
import com.cp2396f09_sem4_grp3.onmart_common.entities.User;
import com.cp2396f09_sem4_grp3.onmart_common.entities.enumeration.EOrderState;
import com.cp2396f09_sem4_grp3.onmart_common.entities.enumeration.ERole;
import com.cp2396f09_sem4_grp3.onmart_service.helper.utils.DataAnalysisUtils;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ExportStockInvoiceRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ImportStockInvoiceRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.OrderRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ProductRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ScheduleDetailsRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ScheduleRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.UserRepository;
import com.cp2396f09_sem4_grp3.onmart_service.service.DashboardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements DashboardService {
        private final UserRepository userRepository;
        private final OrderRepository orderRepository;
        private final ImportStockInvoiceRepository importRepository;
        private final ExportStockInvoiceRepository exportRepository;
        private final ProductRepository productRepository;
        private final ScheduleRepository scheduleRepository;
        private final ScheduleDetailsRepository scheduleDetailsRepository;
        private final ModelMapper modelMapper;

        @Override
        public UserDashboardResponse getUsersDashboardBetweenDatetime(int year, int month, String order) {
                ZonedDateTime startTime = getStartDay(month, year);
                ZonedDateTime endDate = getEndDay(month, year);

                log.info("Start date: " + startTime.toString());
                log.info("End date: " + endDate.toString());

                List<User> disabledUsers;
                List<User> registeredUsers;

                if (order.equals("asc")) {
                        disabledUsers = userRepository.findDisabledByTrashedBetweenSortedAsc(startTime, endDate);
                        registeredUsers = userRepository.findRegisterdByCreatedOnBetweenSortedAsc(startTime, endDate);
                } else {
                        disabledUsers = userRepository.findDisabledByTrashedBetweenSortedDesc(startTime, endDate);
                        registeredUsers = userRepository.findRegisterdByCreatedOnBetweenSortedDesc(startTime, endDate);
                }

                // Find the most registration and disable users in a day.
                DataAnalysisUtils.MostLocalDate mostRegisteredDay;
                if (registeredUsers.isEmpty()) {
                        mostRegisteredDay = DataAnalysisUtils.MostLocalDate.builder()
                                        .count(0)
                                        .date(null)
                                        .build();
                } else {
                        mostRegisteredDay = DataAnalysisUtils.mostCommonCreatedDate(registeredUsers);
                }
                DataAnalysisUtils.MostLocalDate mostDisabledDay;
                if (disabledUsers.isEmpty()) {
                        mostDisabledDay = DataAnalysisUtils.MostLocalDate.builder()
                                        .count(0)
                                        .date(null)
                                        .build();
                } else {
                        mostDisabledDay = DataAnalysisUtils.mostCommonCreatedDate(disabledUsers);
                }

                return UserDashboardResponse.builder()
                                // General
                                .startDate(startTime.toLocalDate())
                                .endDate(endDate.toLocalDate())
                                .month(month)
                                .year(year)
                                // Registration
                                .mostRegisteredDay(mostRegisteredDay.getDate())
                                .numberOfRegistrationInDay(mostRegisteredDay.getCount())
                                .numberOfRegistrationInMonth(registeredUsers.size())
                                .registeredData(DataAnalysisUtils.countUsersCreatedOnAppearances(registeredUsers,
                                                startTime.toLocalDate(), endDate.toLocalDate()))
                                // Disable
                                .mostDisabledDay(mostDisabledDay.getDate())
                                .numberOfDisableInDay(mostDisabledDay.getCount())
                                .numberOfDisableInMonth(disabledUsers.size())
                                .disableData(DataAnalysisUtils.countUsersModifedOnAppearances(disabledUsers,
                                                startTime.toLocalDate(),
                                                endDate.toLocalDate()))
                                // Total
                                .numberOfAccounts(userRepository.count())
                                .numberOfAdmin(userRepository.countByRole(ERole.ADMIN))
                                .numberOfSellers(userRepository.countByRole(ERole.SELLER))
                                .numberOfWarehouceStaffs(userRepository.countByRole(ERole.WAREHOUSE_STAFFS))
                                .numberOfCustomers(userRepository.countByRole(ERole.CUSTOMER))
                                .build();
        }

        @Override
        public OrderDashboardResponse getOrderDashboardBetweenDatetime(int year, int month, String order) {
                ZonedDateTime startDate = getStartDay(month, year);
                ZonedDateTime endDate = getEndDay(month, year);

                List<Order> orders;

                if (order.equals("asc")) {
                        orders = orderRepository.findOrderByCreatedOnBetweenSortedAsc(startDate, endDate);
                } else {
                        orders = orderRepository.findOrderByCreatedOnBetweenSortedDesc(startDate, endDate);
                }

                // Find the most order place in a day
                DataAnalysisUtils.MostLocalDate mostOrderPlacedDay;
                if (orders.isEmpty()) {
                        mostOrderPlacedDay = DataAnalysisUtils.MostLocalDate.builder()
                                        .count(0)
                                        .date(null)
                                        .build();
                } else {
                        mostOrderPlacedDay = DataAnalysisUtils.mostCommonCreatedDate(orders);
                }
                // Find revenue and loyalty callback
                BigDecimal revenue = orderRepository.findRevenueByCreatedOnBetwwen(startDate, endDate);
                BigDecimal loyaltyPointsCallback = orderRepository.findLoyaltyPointsCallbackByCreatedOnBetween(
                                startDate,
                                endDate);

                return OrderDashboardResponse.builder()
                                // General
                                .startDate(startDate.toLocalDate())
                                .endDate(endDate.toLocalDate())
                                .month(month)
                                .year(year)
                                // Order placed report
                                .mostOrderDay(mostOrderPlacedDay.getDate())
                                .numberOfMostOrderInDay(mostOrderPlacedDay.getCount())
                                // Revenue Report
                                .revenue(revenue)
                                .loyatyPointsCallback(loyaltyPointsCallback)
                                .trueRevenue(revenue.subtract(loyaltyPointsCallback))
                                .orderData(DataAnalysisUtils.countOrdersCreatedOnAppearances(orders,
                                                startDate.toLocalDate(),
                                                endDate.toLocalDate()))
                                // Total
                                .numberOfOrdersInMonth(orderRepository.count())
                                .numberOfPlacedOrdersInMonth(
                                                orderRepository.countByStateBetweenDate(EOrderState.PLACED, startDate,
                                                                endDate))
                                .numberOfPreparingOrdersInMonth(
                                                orderRepository.countByStateBetweenDate(EOrderState.PREPARING,
                                                                startDate, endDate))
                                .numberOfDeliveringOrdersInMonth(
                                                orderRepository.countByStateBetweenDate(EOrderState.DELIVERING,
                                                                startDate, endDate))
                                .numberOfDeliveredOrdersInMonth(
                                                orderRepository.countByStateBetweenDate(EOrderState.DELIVERED,
                                                                startDate, endDate))
                                .numberOfDeliveryFailedOrdersInMonth(
                                                orderRepository.countByStateBetweenDate(EOrderState.DELIVERY_FAILED,
                                                                startDate, endDate))
                                .numberOfReceivedOrdersInMonth(
                                                orderRepository.countByStateBetweenDate(EOrderState.RECEIVED, startDate,
                                                                endDate))
                                .numberOfCanceledOrdersInMonth(
                                                orderRepository.countByStateBetweenDate(EOrderState.CANCELED, startDate,
                                                                endDate))
                                .build();
        }

        @Override
        public WarehouseDashboardResponse getWarehouseDashboardBetweenDatetime(int year, int month, String order,
                        int lowStock) {
                ZonedDateTime startDate = getStartDay(month, year);
                ZonedDateTime endDate = getEndDay(month, year);

                List<ImportStockInvoice> importInvoices;
                List<ExportStockInvoice> exportInvoices;
                List<Product> productsWillOutOfStock;
                List<WarehouseWarning> warehouseWarnings;

                // Get data
                if (order.equals("asc")) {
                        importInvoices = importRepository.findImportByCreatedOnBetweenSortedAsc(startDate, endDate);
                        exportInvoices = exportRepository.findExportByCreatedOnBetweenSortedAsc(startDate, endDate);
                } else {
                        importInvoices = importRepository.findImportByCreatedOnBetweenSortedDesc(startDate, endDate);
                        exportInvoices = exportRepository.findExportByCreatedOnBetweenSortedDesc(startDate, endDate);
                }
                productsWillOutOfStock = productRepository.findProductsWithLowStock(lowStock);
                warehouseWarnings = productsWillOutOfStock.stream()
                                .map(w -> modelMapper.map(w, WarehouseWarning.class)).toList();

                // Find the most export and import in a day.
                DataAnalysisUtils.MostLocalDate mostImportDay;
                if (importInvoices.isEmpty()) {
                        mostImportDay = DataAnalysisUtils.MostLocalDate.builder()
                                        .count(0)
                                        .date(null)
                                        .build();
                } else {
                        mostImportDay = DataAnalysisUtils.mostCommonCreatedDate(importInvoices);
                }
                DataAnalysisUtils.MostLocalDate mostExportDay;
                if (importInvoices.isEmpty()) {
                        mostExportDay = DataAnalysisUtils.MostLocalDate.builder()
                                        .count(0)
                                        .date(null)
                                        .build();
                } else {
                        mostExportDay = DataAnalysisUtils.mostCommonCreatedDate(exportInvoices);
                }

                // Find report data
                int numberOfExportsInMonth = exportInvoices.size();
                Long numberOfProductsExportInMonth = exportRepository.sumProductsByBetweenDate(startDate, endDate);
                int numberOfImportsInMonth = importInvoices.size();
                Long numberOfProductsImportInMonth = importRepository.sumProductsByBetweenDate(startDate, endDate);
                Long totalInStock = productRepository.sumInStock();
                Long totalInSell = productRepository.sumInSell();

                return WarehouseDashboardResponse.builder()
                                // General
                                .startDate(startDate.toLocalDate())
                                .endDate(endDate.toLocalDate())
                                .month(month)
                                .year(year)
                                // Most Import -Export
                                .mostImportDay(mostImportDay.getDate())
                                .numberOfMostImportInDay(mostImportDay.getCount())
                                .mostExportDay(mostExportDay.getDate())
                                .numberOfMostExportInDay(mostExportDay.getCount())
                                // All data in the month
                                .warehouseData(DataAnalysisUtils.countWarehouseCreatedOnAppearances(exportInvoices,
                                                importInvoices, startDate.toLocalDate(), endDate.toLocalDate()))
                                // Warning Products
                                .limitForOutOfStock(lowStock)
                                .productMayBeOutOfStock(warehouseWarnings)
                                // General
                                .numberOfExportsInMonth(numberOfExportsInMonth)
                                .numberOfProductsExportInMonth(numberOfProductsExportInMonth)
                                .numberOfImportsInMonth(numberOfImportsInMonth)
                                .numberOfProductsImportInMonth(numberOfProductsImportInMonth)
                                // Total
                                .totalInStock(totalInStock)
                                .totalInSell(totalInSell)
                                .build();
        }

        @Override
        public ScheduleDashboardResponse getScheduleDashboardBetweenDatetime(int year, int month, String order,
                        int top) {
                ZonedDateTime startDate = getStartDay(month, year);
                ZonedDateTime endDate = getEndDay(month, year);

                List<Schedule> schedulesBetweenDateTime = scheduleRepository
                                .findSchedulesBetweenDatetome(startDate.toLocalDate(), endDate.toLocalDate());
                Long morningShiftCounting = 0L;
                Long afternoonShiftCounting = 0L;
                // List of employee work most / least
                List<EmployeeShiftCount> topMostEmployeeWorksInMonth = null;
                List<EmployeeShiftCount> topLeastEmployeeWorksInMonth = null;

                // Find the most working in a day.
                DataAnalysisUtils.MostLocalDate mostWorkingDay;
                if (schedulesBetweenDateTime.isEmpty()) {
                        mostWorkingDay = DataAnalysisUtils.MostLocalDate.builder()
                                        .count(0)
                                        .date(null)
                                        .build();
                } else {
                        mostWorkingDay = DataAnalysisUtils.mostScheduleCreated(schedulesBetweenDateTime);
                        morningShiftCounting = DataAnalysisUtils.countTotalMorningShifts(schedulesBetweenDateTime,
                                        startDate.toLocalDate(), endDate.toLocalDate());
                        afternoonShiftCounting = DataAnalysisUtils.countTotalAfternoonShifts(schedulesBetweenDateTime,
                                        startDate.toLocalDate(), endDate.toLocalDate());
                        topMostEmployeeWorksInMonth = DataAnalysisUtils.findTop3EmployeesMostWorked(
                                        schedulesBetweenDateTime, startDate.toLocalDate(), endDate.toLocalDate());
                        topLeastEmployeeWorksInMonth = DataAnalysisUtils.findTop3EmployeesLeastWorked(
                                        schedulesBetweenDateTime, startDate.toLocalDate(), endDate.toLocalDate());
                }

                List<ScheduleDataByDay> shiftCounting = DataAnalysisUtils.countScheduleDateAppearances(
                                schedulesBetweenDateTime,
                                startDate.toLocalDate(), endDate.toLocalDate());

                return ScheduleDashboardResponse.builder()
                                // General
                                .startDate(startDate.toLocalDate())
                                .endDate(endDate.toLocalDate())
                                .month(month)
                                .year(year)
                                // Day of most schedule
                                .mostEmployeeWorksInDay(mostWorkingDay.getDate())
                                .numberOfMostEmployeeWorksInDay(mostWorkingDay.getCount())
                                // Data
                                .shiftData(shiftCounting)
                                // Top/Least employees worked in month
                                .topMostEmployeeWorksInMonth(topMostEmployeeWorksInMonth)
                                .topLeastEmployeeWorksInMonth(topLeastEmployeeWorksInMonth)
                                // Total
                                .totalOfWorksInMonth(morningShiftCounting + afternoonShiftCounting)
                                .totalOfWorksAtMorningInMonth(morningShiftCounting)
                                .totalOfWorksAtAfternoonInMonth(afternoonShiftCounting)
                                .build();
        }

        private ZonedDateTime getStartDay(int month, int year) {
                YearMonth yearMonth = YearMonth.of(year, month);
                return yearMonth.atDay(1).atStartOfDay(ZoneId.systemDefault());
        }

        private ZonedDateTime getEndDay(int month, int year) {
                YearMonth yearMonth = YearMonth.of(year, month);
                return yearMonth.atEndOfMonth().atTime(23, 59, 59).atZone(ZoneId.systemDefault());
        }
}
