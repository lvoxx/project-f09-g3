package com.cp2396f09_sem4_grp3.onmart_service.service;

import java.util.List;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.FavoriteRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.FavoriteResponse;

public interface FavoriteService {
    List<FavoriteResponse> getFavoriteListByUser(String email);

    FavoriteResponse getFavoriteByUserAndId(String email, Long id);

    FavoriteResponse addToFavoriteByUserAndProduct(String email, FavoriteRequest request);

    void removeFromFavoriteByUserAndId(Long id);
}
