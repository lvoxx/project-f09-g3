package com.cp2396f09_sem4_grp3.onmart_service.controller.internal;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.OrderResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.enumeration.EOrderState;
import com.cp2396f09_sem4_grp3.onmart_service.service.OrderService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/private/orders")
@RequiredArgsConstructor
public class InOrderController {
    @Value("${app.path.fe.root}")
    private String feHomePath;

    @Value("${app.path.fe.received-order}")
    private String feProductReview;

    private final OrderService orderService;

    @GetMapping("/account")
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderResponse> getAllOrdersByAccount(
            @AuthenticationPrincipal UserDetails user,
            @RequestParam(required = false) EOrderState state,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sort) {
        if (state == null) {
            return orderService.listAllOrdersByAccount(page, sort, user.getUsername());
        }
        return orderService.listAllOrdersByAccountAndState(page, sort, user.getUsername(), state);
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateOrderState(@RequestParam EOrderState state, @RequestParam Long id) {
        orderService.updateOrderStateById(state, id);
    }

    @PostMapping("/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelOrder(@RequestParam Long id, @RequestParam(defaultValue = "") String rejectReason) {
        orderService.cancelOrderById(id, rejectReason);
    }

    @PostMapping("/received")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void receivedOrder(HttpServletResponse response, @RequestParam Long id)
            throws IOException {
                orderService.receivedOrderById(id);
        // List<Long> productIds = orderService.receivedOrderById(id);
        // String valuesInParam = productIds.stream()
        //         .map(String::valueOf)
        //         .collect(Collectors.joining(","));
        // response.sendRedirect(feHomePath + feProductReview + "?product-ids=" + valuesInParam);
    }

}
