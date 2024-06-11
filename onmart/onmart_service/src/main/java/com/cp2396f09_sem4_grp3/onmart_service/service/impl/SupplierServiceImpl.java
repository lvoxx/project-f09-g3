package com.cp2396f09_sem4_grp3.onmart_service.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.SupplierRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.SupplierResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Address;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Supplier;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.crud.DataNotFoundException;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.crud.ExistDataException;
import com.cp2396f09_sem4_grp3.onmart_service.repository.AddressRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.SupplierRepository;
import com.cp2396f09_sem4_grp3.onmart_service.service.SupplierService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<SupplierResponse> getAllSuppliers() {
        return supplierRepository.findAll().stream().map(s -> modelMapper.map(s, SupplierResponse.class)).toList();
    }

    @Override
    public SupplierResponse getSupplierById(Long id) {
        return modelMapper.map(supplierRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No supplier found with id " + id)),
                SupplierResponse.class);
    }

    @Override
    public SupplierResponse createSupplier(SupplierRequest request) {
        Optional<Supplier> existSupplier = supplierRepository.findByName(request.getSupplierName());
        if (existSupplier.isPresent()) {
            throw new ExistDataException("Supplier name " + request.getSupplierName() + " already exist");
        }
        Supplier supplier = Supplier.builder()
                .supplierName(request.getSupplierName())
                .contactPerson(request.getContactPerson())
                .contactNumber(request.getContactNumber())
                .email(request.getEmail())
                .build();
        Address address = Address.builder()
                .specificAddress(request.getAddress().getSpecificAddress())
                .ward(request.getAddress().getWard())
                .province(request.getAddress().getProvince())
                .city(request.getAddress().getCity())
                .supplier(supplier)
                .build();
        Supplier res = supplierRepository.saveAndFlush(supplier);

        addressRepository.saveAndFlush(address);

        return modelMapper.map(res, SupplierResponse.class);
    }

    @Override
    public SupplierResponse updateSupplier(SupplierRequest request, Long id) {
        Supplier oldSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ExistDataException("Supplier id " + request.getSupplierName() + " not existed"));

        Address oldAddress = oldSupplier.getAddress();
        if (oldAddress == null) {
            oldAddress = Address.builder()
                    .specificAddress(request.getAddress().getSpecificAddress())
                    .ward(request.getAddress().getWard())
                    .province(request.getAddress().getProvince())
                    .city(request.getAddress().getCity())
                    .supplier(oldSupplier)
                    .build();
        } else {
            // Update Address
            oldAddress.setSpecificAddress(request.getAddress().getSpecificAddress());
            oldAddress.setWard(request.getAddress().getWard());
            oldAddress.setProvince(request.getAddress().getProvince());
            oldAddress.setCity(request.getAddress().getCity());
        }

        // Update Supplier
        oldSupplier.setSupplierName(request.getSupplierName());
        oldSupplier.setContactPerson(request.getContactNumber());
        oldSupplier.setContactNumber(request.getContactNumber());
        oldSupplier.setEmail(request.getEmail());

        addressRepository.saveAndFlush(oldAddress);

        return modelMapper.map(supplierRepository.saveAndFlush(oldSupplier), SupplierResponse.class);
    }

    @Override
    public void deleteSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ExistDataException("Supplier id " + id + " not existed"));
        supplierRepository.delete(supplier);
    }
}
