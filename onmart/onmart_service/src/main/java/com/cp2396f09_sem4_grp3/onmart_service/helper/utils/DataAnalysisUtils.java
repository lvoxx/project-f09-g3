package com.cp2396f09_sem4_grp3.onmart_service.helper.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.order.OrderDataByDay;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.schedule.EmployeeShiftCount;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.schedule.ScheduleDataByDay;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.user.UserDataByDay;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.warehouse.WarehouseDataByDay;
import com.cp2396f09_sem4_grp3.onmart_common.entities.AbstractAuditEntity;
import com.cp2396f09_sem4_grp3.onmart_common.entities.ExportStockInvoice;
import com.cp2396f09_sem4_grp3.onmart_common.entities.ImportStockInvoice;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Order;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Schedule;
import com.cp2396f09_sem4_grp3.onmart_common.entities.ScheduleDetails;
import com.cp2396f09_sem4_grp3.onmart_common.entities.enumeration.EScheduleShift;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class DataAnalysisUtils {
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class MostLocalDate {
        private LocalDate date;
        private Integer count;
    }

    public static <T extends Schedule> MostLocalDate mostScheduleCreated(List<T> list) {
        Map<LocalDate, Integer> map = new HashMap<>();

        for (T t : list) {
            map.put(t.getCreatedOn().toLocalDate(), t.getScheduleDetails().size());
        }

        Entry<LocalDate, Integer> max = null;

        for (Entry<LocalDate, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }

        return MostLocalDate.builder()
                .date(max.getKey())
                .count(max.getValue())
                .build();
    }

    public static <T extends AbstractAuditEntity> MostLocalDate mostCommonCreatedDate(List<T> list) {
        Map<LocalDate, Integer> map = new HashMap<>();

        for (T t : list) {
            Integer val = map.get(t.getCreatedOn().toLocalDate());
            map.put(t.getCreatedOn().toLocalDate(), val == null ? 1 : val + 1);
        }

        Entry<LocalDate, Integer> max = null;

        for (Entry<LocalDate, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }

        return MostLocalDate.builder()
                .date(max.getKey())
                .count(max.getValue())
                .build();
    }

    public static <T extends AbstractAuditEntity> MostLocalDate mostCommonModifiedDate(List<T> list) {
        Map<LocalDate, Integer> map = new HashMap<>();

        for (T t : list) {
            Integer val = map.get(t.getLastModifiedOn().toLocalDate());
            map.put(t.getLastModifiedOn().toLocalDate(), val == null ? 1 : val + 1);
        }

        Entry<LocalDate, Integer> max = null;

        for (Entry<LocalDate, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }

        return MostLocalDate.builder()
                .date(max.getKey())
                .count(max.getValue())
                .build();
    }

    public static <T extends AbstractAuditEntity> List<UserDataByDay> countUsersCreatedOnAppearances(List<T> list,
            LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

        // Create a map with dates in the range initialized to 0
        Map<LocalDate, Long> appearancesMap = Stream.iterate(startDate, date -> date.plusDays(1))
                .limit(java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1)
                .collect(Collectors.toMap(
                        date -> date,
                        date -> 0L,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));

        // Count appearances by day
        for (T user : list) {
            LocalDate createdDate = user.getCreatedOn().toLocalDate();
            appearancesMap.put(createdDate, appearancesMap.getOrDefault(createdDate, 0L) + 1);
        }

        // Convert the map to a list of DayAppearances objects
        return appearancesMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new UserDataByDay(entry.getKey().format(formatter), entry.getValue()))
                .collect(Collectors.toList());
    }

    public static <T extends AbstractAuditEntity> List<UserDataByDay> countUsersModifedOnAppearances(List<T> list,
            LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

        // Create a map with dates in the range initialized to 0
        Map<LocalDate, Long> appearancesMap = Stream.iterate(startDate, date -> date.plusDays(1))
                .limit(java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1)
                .collect(Collectors.toMap(
                        date -> date,
                        date -> 0L,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));

        // Count appearances by day
        for (T user : list) {
            LocalDate createdDate = user.getLastModifiedOn().toLocalDate();
            appearancesMap.put(createdDate, appearancesMap.getOrDefault(createdDate, 0L) + 1);
        }

        // Convert the map to a list of DayAppearances objects
        return appearancesMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new UserDataByDay(entry.getKey().format(formatter), entry.getValue()))
                .collect(Collectors.toList());
    }

    public static <T extends Order> List<OrderDataByDay> countOrdersCreatedOnAppearances(List<T> orders,
            LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
        Map<String, OrderDataByDay> appearancesMap = new HashMap<>();

        // Initialize the map with all days between start and end dates
        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            String day = date.format(formatter);
            appearancesMap.put(day, new OrderDataByDay(day));
            date = date.plusDays(1);
        }

        // Count appearances by day
        for (Order order : orders) {
            String day = order.getCreatedOn().format(formatter);
            OrderDataByDay orderData = appearancesMap.getOrDefault(day, new OrderDataByDay(day));

            if (orderData != null) {
                switch (order.getState()) {
                    case PLACED:
                        orderData.setNumberOfPlacedOrders(orderData.getNumberOfPlacedOrders() + 1);
                        break;
                    case PREPARING:
                        orderData.setNumberOfPreparingOrders(orderData.getNumberOfPreparingOrders() + 1);
                        break;
                    case DELIVERING:
                        orderData.setNumberOfDeliveringOrders(orderData.getNumberOfDeliveringOrders() + 1);
                        break;
                    case DELIVERED:
                        orderData.setNumberOfDeliveredOrders(orderData.getNumberOfDeliveredOrders() + 1);
                        break;
                    case DELIVERY_FAILED:
                        orderData.setNumberOfDeliveryFailedOrders(orderData.getNumberOfDeliveryFailedOrders() + 1);
                        break;
                    case RECEIVED:
                        orderData.setNumberOfReceivedOrders(orderData.getNumberOfReceivedOrders() + 1);
                        break;
                    case CANCELED:
                        orderData.setNumberOfCanceledOrders(orderData.getNumberOfCanceledOrders() + 1);
                        break;
                }
            }
            appearancesMap.put(day, orderData);
        }

        return appearancesMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Entry::getValue)
                .collect(Collectors.toList());
    }

    public static List<WarehouseDataByDay> countWarehouseCreatedOnAppearances(
            List<ExportStockInvoice> exportInvoices,
            List<ImportStockInvoice> importInvoices,
            LocalDate startDate,
            LocalDate endDate) {
        Map<String, Long> exportCountByDay = groupInvoicesByDay(exportInvoices);
        Map<String, Long> importCountByDay = groupInvoicesByDay(importInvoices);
        List<WarehouseDataByDay> result = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM");

        // Iterate over each date in the range
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            String day = date.format(dateFormatter);

            // Get counts from the maps
            long numberOfImports = importCountByDay.getOrDefault(day, 0L);
            long numberOfExports = exportCountByDay.getOrDefault(day, 0L);

            // Create a WarehouseDataByDay instance
            WarehouseDataByDay warehouseDataByDay = new WarehouseDataByDay(day);
            warehouseDataByDay.setNumberOfImports(numberOfImports);
            warehouseDataByDay.setNumberOfExports(numberOfExports);

            // Add to the result list
            result.add(warehouseDataByDay);
        }

        return result;
    }

    public static List<ScheduleDataByDay> countScheduleDateAppearances(
            List<Schedule> scheduleData,
            LocalDate startDate,
            LocalDate endDate) {
        List<ScheduleDataByDay> shiftCounts = new ArrayList<>();

        // Initialize the list with empty counts for each day in the range
        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            shiftCounts.add(new ScheduleDataByDay(date.toString()));
            date = date.plusDays(1);
        }

        // Iterate through the schedule data to count shifts
        for (Schedule schedule : scheduleData) {
            LocalDate shiftDate = schedule.getDate();
            // Check if the shift date is within the specified range
            if (!shiftDate.isBefore(startDate) && !shiftDate.isAfter(endDate)) {
                ScheduleDataByDay shiftCount = shiftCounts.get((int) startDate.until(shiftDate).getDays());
                // Increment morning or afternoon shift count based on the shift type
                for (ScheduleDetails details : schedule.getScheduleDetails()) {
                    if (details.getScheduleShift() == EScheduleShift.MORNING) {
                        shiftCount.setNumberOfMorningShift(shiftCount.getNumberOfMorningShift() + 1);
                    } else if (details.getScheduleShift() == EScheduleShift.AFTERNOON) {
                        shiftCount.setNumberOfAfternoonShift(shiftCount.getNumberOfAfternoonShift() + 1);
                    }
                }
            }
        }

        return shiftCounts;
    }

    public static Long countTotalMorningShifts(List<Schedule> scheduleData, LocalDate startDate, LocalDate endDate) {
        Long totalMorningShifts = 0L;

        // Iterate through the schedule data to count morning shifts
        for (Schedule schedule : scheduleData) {
            LocalDate shiftDate = schedule.getDate();
            // Check if the shift date is within the specified range
            if (!shiftDate.isBefore(startDate) && !shiftDate.isAfter(endDate)) {
                // Increment morning shift count for each morning shift in the schedule
                for (ScheduleDetails details : schedule.getScheduleDetails()) {
                    if (details.getScheduleShift() == EScheduleShift.MORNING) {
                        totalMorningShifts++;
                    }
                }
            }
        }

        return totalMorningShifts;
    }

    public static Long countTotalAfternoonShifts(List<Schedule> scheduleData, LocalDate startDate, LocalDate endDate) {
        Long totalMorningShifts = 0L;

        // Iterate through the schedule data to count morning shifts
        for (Schedule schedule : scheduleData) {
            LocalDate shiftDate = schedule.getDate();
            // Check if the shift date is within the specified range
            if (!shiftDate.isBefore(startDate) && !shiftDate.isAfter(endDate)) {
                // Increment morning shift count for each morning shift in the schedule
                for (ScheduleDetails details : schedule.getScheduleDetails()) {
                    if (details.getScheduleShift() == EScheduleShift.AFTERNOON) {
                        totalMorningShifts++;
                    }
                }
            }
        }

        return totalMorningShifts;
    }

    public static List<EmployeeShiftCount> findTop3EmployeesMostWorked(List<Schedule> scheduleData, LocalDate startDate,
            LocalDate endDate) {
        Map<String, Long> employeeWorkCounting = new HashMap<>();

        // Iterate through the schedule data to calculate work hours for each employee
        for (Schedule schedule : scheduleData) {
            LocalDate shiftDate = schedule.getDate();
            // Check if the shift date is within the specified range
            if (!shiftDate.isBefore(startDate) && !shiftDate.isAfter(endDate)) {
                for (ScheduleDetails details : schedule.getScheduleDetails()) {
                    Long employeeId = details.getEmployeeId();
                    String employeeName = details.getEmployeeName();
                    Long workHours = employeeWorkCounting.getOrDefault(employeeId + "$" + employeeName, 0L);
                    // Assuming each shift is one hour
                    employeeWorkCounting.put(employeeId + "$" + employeeName, workHours + 1);
                }
            }
        }

        // Sort the map by work hours in descending order
        return employeeWorkCounting.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .map(entry -> {
                    String[] parts = splitEmployeeString(entry.getKey());
                    return new EmployeeShiftCount(Long.valueOf(parts[0]), parts[1], entry.getValue());
                })
                .collect(Collectors.toList());
    }

    public static List<EmployeeShiftCount> findTop3EmployeesLeastWorked(List<Schedule> scheduleData,
            LocalDate startDate,
            LocalDate endDate) {
        Map<String, Long> employeeWorkCounting = new HashMap<>();

        // Iterate through the schedule data to calculate work hours for each employee
        for (Schedule schedule : scheduleData) {
            LocalDate shiftDate = schedule.getDate();
            // Check if the shift date is within the specified range
            if (!shiftDate.isBefore(startDate) && !shiftDate.isAfter(endDate)) {
                for (ScheduleDetails details : schedule.getScheduleDetails()) {
                    Long employeeId = details.getEmployeeId();
                    String employeeName = details.getEmployeeName();
                    Long shiftCount = employeeWorkCounting.getOrDefault(employeeId + "$" + employeeName, 0L);
                    // Assuming each shift is one hour
                    employeeWorkCounting.put(employeeId + "$" + employeeName, shiftCount + 1);
                }
            }
        }

        return employeeWorkCounting.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue())
                .limit(3)
                .map(entry -> {
                    String[] parts = splitEmployeeString(entry.getKey());
                    return new EmployeeShiftCount(Long.valueOf(parts[0]), parts[1], entry.getValue());
                })
                .collect(Collectors.toList());
    }

    // Helper method to group invoices by day
    private static Map<String, Long> groupInvoicesByDay(List<? extends AbstractAuditEntity> invoices) {
        return invoices.stream()
                .collect(Collectors.groupingBy(
                        invoice -> invoice.getCreatedOn().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM")),
                        Collectors.counting()));
    }

    // Cut 2 string seperated by $
    public static String[] splitEmployeeString(String employeeString) {
        return employeeString.split("\\$", 2);
    }

    private DataAnalysisUtils() {
    }
}
