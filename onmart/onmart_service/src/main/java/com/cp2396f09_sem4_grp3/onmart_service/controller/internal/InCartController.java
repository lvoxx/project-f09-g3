package com.cp2396f09_sem4_grp3.onmart_service.controller.internal;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.CartRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.CartResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.CartService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/private/carts")
@RequiredArgsConstructor
public class InCartController {
    private final CartService cartService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CartResponse> listCartList(@AuthenticationPrincipal UserDetails user) {
        return cartService.getCartListByUser(user.getUsername());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CartResponse getCartById(@AuthenticationPrincipal UserDetails user, @PathVariable Long id) {
        return cartService.getCartByUserAndId(user.getUsername(), id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartResponse addToCartList(@AuthenticationPrincipal UserDetails user, @RequestBody CartRequest request) {
        return cartService.addToCartByUserAndProduct(user.getUsername(), request);
    }

    @PutMapping("/increase/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public void increaseOneProductInCart(@PathVariable Long id) {
        cartService.increaseOneProductById(id);
    }

    @PutMapping("/decrease/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public void decreaseOneProductInCart(@PathVariable Long id) {
        cartService.decreaseOneProductById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFromCartList(@AuthenticationPrincipal UserDetails user, @PathVariable Long id) {
        cartService.removeFromCartById(id);
    }
}
