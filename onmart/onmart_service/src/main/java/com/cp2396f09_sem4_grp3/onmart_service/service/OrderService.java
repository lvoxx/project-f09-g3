package com.cp2396f09_sem4_grp3.onmart_service.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.OrderRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.OrderResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.enumeration.EOrderState;

public interface OrderService {
    Page<OrderResponse> listAllOrders(Integer page, String sort);

    Page<OrderResponse> listAllOrdersByState(Integer page, String sort, EOrderState state);

    Page<OrderResponse> listAllOrdersByAccount(Integer page, String sort, String email);

    Page<OrderResponse> listAllOrdersByAccountAndState(Integer page, String sort, String email, EOrderState state);

    OrderResponse createOrderToDb(OrderRequest request, String email);

    String createOrderRequestToLocal(OrderRequest request);

    OrderRequest findOrderRequestInLocal(String id);

    void updateOrderStateById(EOrderState state, Long id);

    void cancelOrderById(Long id, String rejectReason);

    List<Long> receivedOrderById(Long id);
}
