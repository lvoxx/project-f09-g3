package com.cp2396f09_sem4_grp3.onmart_service.service;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.order.OrderDashboardResponse;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.schedule.ScheduleDashboardResponse;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.user.UserDashboardResponse;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.warehouse.WarehouseDashboardResponse;

public interface DashboardService {
    // User Dashboard
    UserDashboardResponse getUsersDashboardBetweenDatetime(int year, int month, String order);

    // Order Dashboard
    OrderDashboardResponse getOrderDashboardBetweenDatetime(int year, int month, String order);

    // Warehouse Dashboard
    WarehouseDashboardResponse getWarehouseDashboardBetweenDatetime(int year, int month, String order, int lowStock);

    // Schedule Dashboard
    ScheduleDashboardResponse getScheduleDashboardBetweenDatetime(int year, int month, String order, int top);
}
