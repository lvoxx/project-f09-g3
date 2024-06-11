package com.cp2396f09_sem4_grp3.onmart_service.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.CartRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.CartResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Cart;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Product;
import com.cp2396f09_sem4_grp3.onmart_common.entities.User;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.crud.DataNotFoundException;
import com.cp2396f09_sem4_grp3.onmart_service.repository.CartRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ProductRepository;
import com.cp2396f09_sem4_grp3.onmart_service.service.CartService;
import com.cp2396f09_sem4_grp3.onmart_service.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public List<CartResponse> getCartListByUser(String email) {
        return cartRepository.findAllByUserEmail(email).stream()
                .map(c -> modelMapper.map(c, CartResponse.class)).toList();
    }

    @Override
    public CartResponse getCartByUserAndId(String email, Long id) {
        return modelMapper.map(cartRepository.findAllByUserEmailAndId(email, id), CartResponse.class);
    }

    @Override
    public CartResponse addToCartByUserAndProduct(String email, CartRequest request) {
        User user = userService.findByEmail(email);
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new DataNotFoundException("No product found."));
        Cart cart = Cart.builder()
                .user(user)
                .product(product)
                .build();
        return modelMapper.map(cartRepository.saveAndFlush(cart), CartResponse.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeFromCartById(Long id) {
        Cart oldCart = cartRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No cart found."));

        // Remove the cart reference from the product's carts collection
        Product product = oldCart.getProduct();
        if (product != null) {
            product.getCarts().remove(oldCart);
        }

        // Remove the cart reference from the user's carts collection
        User user = oldCart.getUser();
        if (user != null) {
            user.getCarts().remove(oldCart);
        }

        cartRepository.deleteById(id);
    }

    @Override
    public Cart getCartByUserIdAndProductId(Long userId, Long productId) {
        return cartRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new DataNotFoundException("No cart found."));
    }

    @Override
    public void increaseOneProductById(Long id) {
        cartRepository.increaseCartsById(id);
    }

    @Override
    public void decreaseOneProductById(Long id) {
        cartRepository.decreaseCartsById(id);
    }

}
