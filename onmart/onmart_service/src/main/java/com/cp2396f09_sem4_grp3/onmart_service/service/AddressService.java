package com.cp2396f09_sem4_grp3.onmart_service.service;

import java.util.List;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.AddressRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.AddressResponse;

public interface AddressService {
    List<AddressResponse> getAllAddressesByUserEmail(String userEmail);

    AddressResponse createAddressByUserEmail(String userEmail, AddressRequest request);

    AddressResponse updateAddressById(Long id, AddressRequest request);

    void deleteAddressById(Long id, String userEmail);

    void setAddressDefaultById(Long id);

    AddressResponse getDafaultAddressByUserEmail(String email);

}
