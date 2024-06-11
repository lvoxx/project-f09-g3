package com.cp2396f09_sem4_grp3.onmart_service.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.OrderRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.OrderResponse;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.PaymentHistoryResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Cart;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Order;
import com.cp2396f09_sem4_grp3.onmart_common.entities.OrderItem;
import com.cp2396f09_sem4_grp3.onmart_common.entities.PaymentHistory;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Product;
import com.cp2396f09_sem4_grp3.onmart_common.entities.User;
import com.cp2396f09_sem4_grp3.onmart_common.entities.enumeration.EOrderState;
import com.cp2396f09_sem4_grp3.onmart_common.entities.enumeration.EPaymentMethod;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.crud.DataNotFoundException;
import com.cp2396f09_sem4_grp3.onmart_service.repository.OrderItemRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.OrderRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ProductRepository;
import com.cp2396f09_sem4_grp3.onmart_service.service.CartService;
import com.cp2396f09_sem4_grp3.onmart_service.service.OrderService;
import com.cp2396f09_sem4_grp3.onmart_service.service.PaymentHistoryService;
import com.cp2396f09_sem4_grp3.onmart_service.service.ProductService;
import com.cp2396f09_sem4_grp3.onmart_service.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Value("${app.jpa.max-items-in-page}")
    private int maxItems;

    // Local Orders
    private final Map<String, OrderRequest> orderRequestStore = new ConcurrentHashMap<>();

    private final CartService cartService;
    private final ModelMapper modelMapper;
    private final OrderItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final UserService userService;
    private final PaymentHistoryService paymentHistoryService;

    @Override
    public OrderResponse createOrderToDb(OrderRequest request, String email) {
        // Find logined Customer
        User customer = userService.findByEmail(email);
        // Make Order
        Order order = Order.builder()
                .customerId(customer.getId())
                .customerName(customer.getFirstName() + " " + customer.getLastName())
                .deliveryAddress(request.getDeliveryAddress())
                .deliveryFee(request.getDeliveryFee())
                .note(request.getNote())
                .totalPrice(request.getTotalPrice())
                .build();
        // Update loyalty points in customer account
        if (request.getLoyaltyPointsPay().compareTo(BigDecimal.valueOf(0L)) > 0) {
            order.setLoyaltyPointsPay(request.getLoyaltyPointsPay());
            userService.addLoyaltyPoints(request.getLoyaltyPointsPay().negate(), customer.getId());
        }
        // Save Order to DB
        Order response = orderRepository.saveAndFlush(order);
        // Create and Save OrderItem to DB
        request.getOrderDetails().forEach(details -> {
            Product product = productRepository.findById(details.getProductId())
                    .orElseThrow(() -> new DataNotFoundException("No product found."));
            OrderItem item = new OrderItem();
            if (product.getPromotion() != null) {
                BigDecimal discountPercentage = BigDecimal.valueOf(product.getPromotion().getDiscountPercent())
                        .divide(BigDecimal.valueOf(100));

                // Calcudlate discount amount
                BigDecimal discountAmount = product.getSellPrice().multiply(discountPercentage);

                // Calculate the discounted price
                BigDecimal discountedPrice = product.getSellPrice().subtract(discountAmount)
                        .multiply(BigDecimal.valueOf(details.getQuantity()));

                item = OrderItem.builder()
                        .productId(product.getId())
                        .productName(product.getName())
                        .quantity(details.getQuantity())
                        .productPrice(product.getSellPrice())
                        .discountPercent(product.getPromotion().getDiscountPercent())
                        .discountName(product.getPromotion().getName())
                        .subTotalPrice(discountedPrice)
                        .order(response)
                        .build();
            } else {
                item = OrderItem.builder()
                        .productId(product.getId())
                        .productName(product.getName())
                        .quantity(details.getQuantity())
                        .productPrice(product.getSellPrice())
                        .discountPercent(0)
                        .discountName("No discount")
                        .subTotalPrice(product.getSellPrice().multiply(BigDecimal.valueOf(details.getQuantity())))
                        .order(response)
                        .build();
            }
            itemRepository.saveAndFlush(item);
            // Update product sell quantity
            product.setInSellQuantity(product.getInSellQuantity() - details.getQuantity());
            productRepository.saveAndFlush(product);
            productService.increasePurchaseByOne(product.getId());

            // Update user cart
            Cart cart = cartService.getCartByUserIdAndProductId(customer.getId(), details.getProductId());
            cartService.removeFromCartById(cart.getId());
        });
        // Create payment history
        PaymentHistory history = PaymentHistory.builder()
                .customerId(customer.getId())
                .customerName(customer.getFirstName() + customer.getLastName())
                .amount(response.getTotalPrice())
                .currency("VND")
                .order(response)
                .description(String.format("Pay order #%d by VNPay", response.getId()))
                .paymentMethod(EPaymentMethod.VNPAY)
                .build();
        PaymentHistoryResponse paymentHistoryResponse = paymentHistoryService.createPayment(history);

        OrderResponse orderResponse = modelMapper.map(response, OrderResponse.class);
        orderResponse.setPaymentHistory(paymentHistoryResponse);
        return orderResponse;
    }

    @Override
    public Page<OrderResponse> listAllOrders(Integer page, String sort) {
        Pageable paging = PageRequest.of(page, maxItems, Direction.DESC, sort);
        return orderRepository.findAll(paging).map(order -> modelMapper.map(order, OrderResponse.class));
    }

    @Override
    public Page<OrderResponse> listAllOrdersByAccount(Integer page, String sort, String email) {
        User customer = userService.findByEmail(email);
        Pageable paging = PageRequest.of(page, maxItems, Direction.DESC, sort);
        return orderRepository.findOrderByCustomerId(customer.getId(), paging)
                .map(order -> modelMapper.map(order, OrderResponse.class));
    }

    @Override
    public Page<OrderResponse> listAllOrdersByAccountAndState(Integer page, String sort, String email,
            EOrderState state) {
        Pageable paging = PageRequest.of(page, maxItems, Direction.DESC, sort);
        User user = userService.findByEmail(email);
        return orderRepository.findOrderByStateAndCustomerId(state, user.getId(), paging)
                .map(order -> modelMapper.map(order, OrderResponse.class));
    }

    @Override
    public Page<OrderResponse> listAllOrdersByState(Integer page, String sort, EOrderState state) {
        Pageable paging = PageRequest.of(page, maxItems, Direction.DESC, sort);
        return orderRepository.findOrderByState(state, paging)
                .map(order -> modelMapper.map(order, OrderResponse.class));
    }

    @Override
    public String createOrderRequestToLocal(OrderRequest request) {
        // Make ID
        String orderId = UUID.randomUUID().toString();
        orderRequestStore.put(orderId, request);
        return orderId;
    }

    @Override
    public OrderRequest findOrderRequestInLocal(String id) {
        return orderRequestStore.get(id);
    }

    @Override
    public void cancelOrderById(Long id, String rejectReason) {
        orderRepository.cancelOrderById(rejectReason, id);
        Order order = orderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("No Order found"));

        userService.addLoyaltyPoints(order.getLoyaltyPointsPay(), order.getCustomerId());
    }

    @Override
    public List<Long> receivedOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No Order found"));
        order.setState(EOrderState.RECEIVED);
        order = orderRepository.save(order);
        return order.getOrderItems().stream().map(OrderItem::getProductId).toList();
    }

    @Override
    public void updateOrderStateById(EOrderState state, Long id) {
        orderRepository.updateOrderStateById(state, id);
    }

}
