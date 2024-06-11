package com.cp2396f09_sem4_grp3.onmart_service.controller.external;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.OrderResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.enumeration.EOrderState;
import com.cp2396f09_sem4_grp3.onmart_service.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/public/orders")
@RequiredArgsConstructor
@Slf4j
public class ExOrderController {

    private final OrderService orderService;

    @GetMapping
    public Page<OrderResponse> getAllOrders(
            @RequestParam(required = false) EOrderState state,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sort) {
        if (state == null) {
            log.info("All Orders");
            return orderService.listAllOrders(page, sort);
        }
        log.info("All Orders by state");
        return orderService.listAllOrdersByState(page, sort, state);
    }

}
