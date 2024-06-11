package com.cp2396f09_sem4_grp3.onmart_service.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.AddressRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.AddressResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Address;
import com.cp2396f09_sem4_grp3.onmart_common.entities.User;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.crud.DataNotFoundException;
import com.cp2396f09_sem4_grp3.onmart_service.repository.AddressRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.UserRepository;
import com.cp2396f09_sem4_grp3.onmart_service.service.AddressService;
import com.cp2396f09_sem4_grp3.onmart_service.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public List<AddressResponse> getAllAddressesByUserEmail(String userEmail) {
        User user = userService.findByEmail(userEmail);
        return user.getAddress().stream().map(address -> modelMapper.map(address, AddressResponse.class)).toList();
    }

    @Override
    public AddressResponse createAddressByUserEmail(String userEmail, AddressRequest request) {
        User user = userService.findByEmail(userEmail);
        Address address = Address.builder()
                .specificAddress(request.getSpecificAddress())
                .ward(request.getWard())
                .province(request.getProvince())
                .city(request.getCity())
                .user(user)
                .build();
        return modelMapper.map(addressRepository.saveAndFlush(address), AddressResponse.class);
    }

    @Override
    public AddressResponse updateAddressById(Long id, AddressRequest request) {
        Address olAddress = addressRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No address found."));

        olAddress.setSpecificAddress(request.getSpecificAddress());
        olAddress.setWard(request.getWard());
        olAddress.setProvince(request.getProvince());
        olAddress.setCity(request.getCity());

        return modelMapper.map(addressRepository.saveAndFlush(olAddress), AddressResponse.class);
    }

    @Override
    public void deleteAddressById(Long id, String userEmail) {
        User user = userService.findByEmail(userEmail);
        Address address = user.getAddress().stream().filter(a -> a.getId() == id).findFirst().get();
        user.getAddress().remove(address);
        userRepository.saveAndFlush(user);

        addressRepository.deleteById(id);
    }

    @Override
    public void setAddressDefaultById(Long id) {
        List<Address> userAddresses = addressRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No address found.")).getUser().getAddress();
        if (!userAddresses.isEmpty()) {
            userAddresses.forEach(address -> address.setDefault(address.getId().equals(id)));
            addressRepository.saveAllAndFlush(userAddresses);
            return;
        }
        throw new DataNotFoundException("No addresses in user.");
    }

    @Override
    public AddressResponse getDafaultAddressByUserEmail(String email) {
        User user = userService.findByEmail(email);
        Optional<Address> address = addressRepository.findDefaultAddressByUserId(user.getId());
        if (!address.isPresent()) {
            List<AddressResponse> list = getAllAddressesByUserEmail(email);
            if (list.isEmpty()) {
                throw new DataNotFoundException("No default address found.");
            }
            return list.get(0);
        }
        return modelMapper.map(address.get(), AddressResponse.class);
    }

}
