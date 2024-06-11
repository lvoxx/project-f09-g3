package com.cp2396f09_sem4_grp3.onmart_service.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.FavoriteRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.FavoriteResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Favorite;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Product;
import com.cp2396f09_sem4_grp3.onmart_common.entities.User;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.crud.DataNotFoundException;
import com.cp2396f09_sem4_grp3.onmart_service.repository.FavoriteRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ProductRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.UserRepository;
import com.cp2396f09_sem4_grp3.onmart_service.service.FavoriteService;
import com.cp2396f09_sem4_grp3.onmart_service.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final FavoriteRepository favoriteRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<FavoriteResponse> getFavoriteListByUser(String email) {
        return favoriteRepository.findAllByUserEmail(email).stream()
                .map(f -> modelMapper.map(f, FavoriteResponse.class)).toList();
    }

    @Override
    public FavoriteResponse getFavoriteByUserAndId(String email, Long id) {
        return modelMapper.map(favoriteRepository.findAllByUserEmailAndId(email, id), FavoriteResponse.class);
    }

    @Override
    public FavoriteResponse addToFavoriteByUserAndProduct(String email, FavoriteRequest request) {
        User user = userService.findByEmail(email);
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new DataNotFoundException("No product found."));
        Favorite favorite = Favorite.builder()
                .user(user)
                .product(product)
                .build();
        return modelMapper.map(favoriteRepository.saveAndFlush(favorite), FavoriteResponse.class);
    }

    @Override
    public void removeFromFavoriteByUserAndId(Long id) {
        Favorite oldFavorite = favoriteRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No favorite found."));

        oldFavorite.getProduct().getFavorites().remove(oldFavorite);
        productRepository.saveAndFlush(oldFavorite.getProduct());

        oldFavorite.getUser().getFavorites().remove(oldFavorite);
        userRepository.saveAndFlush(oldFavorite.getUser());

        favoriteRepository.delete(oldFavorite);
    }
}
