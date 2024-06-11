package com.cp2396f09_sem4_grp3.onmart_service.service;

import java.util.List;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.CartRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.CartResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Cart;

public interface CartService {
    List<CartResponse> getCartListByUser(String email);

    CartResponse getCartByUserAndId(String email, Long id);

    Cart getCartByUserIdAndProductId(Long userId, Long productId);

    CartResponse addToCartByUserAndProduct(String email, CartRequest request);

    void increaseOneProductById(Long id);

    void decreaseOneProductById(Long id);

    void removeFromCartById(Long id);
}
