package com.cp2396f09_sem4_grp3.onmart_service.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.OrderRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.VNPayResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.OrderService;
import com.cp2396f09_sem4_grp3.onmart_service.service.VNPayService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    @Value("${app.path.fe.root}")
    private String feHomePath;

    @Value("${app.path.fe.pay-success}")
    private String fePaySuccessPath;

    @Value("${app.path.fe.pay-failed}")
    private String fePayFailedPath;

    private final VNPayService vnPayService;
    private final OrderService orderService;

    @PostMapping("/vn-pay")
    @ResponseStatus(HttpStatus.CREATED)
    public VNPayResponse pay(
            HttpServletRequest request,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody OrderRequest orderRequest) throws IOException {
        String orderId = orderService.createOrderRequestToLocal(orderRequest);
        return vnPayService.createVnPayPayment(request, orderId, userDetails.getUsername());
    }

    @GetMapping("/vn-pay-callback/{orderId}/{email}")
    @ResponseStatus(HttpStatus.CREATED)
    public void payCallbackHandler(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable("orderId") String orderId,
            @PathVariable("email") String email) throws IOException {
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            OrderRequest order = orderService.findOrderRequestInLocal(orderId);
            orderService.createOrderToDb(order, email);
            response.sendRedirect(feHomePath + fePaySuccessPath);
        } else {
            // return new ResponseObject<>(HttpStatus.BAD_REQUEST, "Failed", null);
            response.sendRedirect(feHomePath + fePayFailedPath);
        }
    }
}
