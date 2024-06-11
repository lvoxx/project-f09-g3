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

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.FavoriteRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.FavoriteResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.FavoriteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/private/favorites")
@RequiredArgsConstructor
public class InFavoriteController {
    private final FavoriteService favoriteService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FavoriteResponse> listFavoriteList(@AuthenticationPrincipal UserDetails user) {
        return favoriteService.getFavoriteListByUser(user.getUsername());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FavoriteResponse getFavoriteById(@AuthenticationPrincipal UserDetails user, @PathVariable Long id) {
        return favoriteService.getFavoriteByUserAndId(user.getUsername(), id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FavoriteResponse addToFavoriteList(@AuthenticationPrincipal UserDetails user, @RequestBody FavoriteRequest request) {
        return favoriteService.addToFavoriteByUserAndProduct(user.getUsername(), request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFromFavoriteList(@AuthenticationPrincipal UserDetails user, @PathVariable Long id) {
        favoriteService.removeFromFavoriteByUserAndId(id);
    }
}
