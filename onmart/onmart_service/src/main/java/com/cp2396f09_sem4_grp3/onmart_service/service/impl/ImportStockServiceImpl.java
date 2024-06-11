package com.cp2396f09_sem4_grp3.onmart_service.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ImportInvoiceRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ImportInvoiceResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.ImportStockInvoice;
import com.cp2396f09_sem4_grp3.onmart_common.entities.ImportStockInvoiceDetails;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Product;
import com.cp2396f09_sem4_grp3.onmart_common.entities.User;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ImportStockInvoiceDetailsRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ImportStockInvoiceRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ProductRepository;
import com.cp2396f09_sem4_grp3.onmart_service.service.ImportStockService;
import com.cp2396f09_sem4_grp3.onmart_service.service.ProductService;
import com.cp2396f09_sem4_grp3.onmart_service.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ImportStockServiceImpl implements ImportStockService {

    @Value("${app.jpa.max-items-in-page}")
    private int maxItems;

    private final ImportStockInvoiceRepository importRepository;
    private final ImportStockInvoiceDetailsRepository detailsRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public Page<ImportInvoiceResponse> getAllInvoices(Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, maxItems, Sort.by(sortBy));
        return importRepository.findAll(paging)
                .map(inv -> modelMapper.map(inv, ImportInvoiceResponse.class));
    }

    @Override
    public ImportInvoiceResponse getInvoiceById(Long id) {
        return modelMapper.map(importRepository.findById(id), ImportInvoiceResponse.class);
    }

    @Override
    public ImportInvoiceResponse createInvoice(String email, List<ImportInvoiceRequest> request) {
        User user = userService.findByEmail(email);
        ImportStockInvoice invoice = ImportStockInvoice.builder()
                .creatorId(user.getId())
                .creatorName(user.getFirstName() + user.getLastName())
                .build();
        ImportStockInvoice response = importRepository.saveAndFlush(invoice);
        List<Product> products = productRepository
                .findAllById(
                        request.stream()
                                .map(ImportInvoiceRequest::getProductId)
                                .collect(Collectors.toList()));
        log.info(request.toString());
        // Sort 2 list
        products.sort((item1, item2) -> item1.getId().compareTo(item2.getId()));
        request.sort((item1, item2) -> item1.getProductId().compareTo(item2.getProductId()));

        // Assign value to details
        java.util.List<ImportStockInvoiceDetails> details = new ArrayList<>();
        for (int i = 0; i < products.size(); ++i) {
            details.add(ImportStockInvoiceDetails.builder()
                    .productId(products.get(i).getId())
                    .productName(products.get(i).getName())
                    .quantity(request.get(i).getQuantity())
                    .importStockInvoice(response)
                    .build());
            productService.importProduct(request.get(i).getQuantity(), products.get(i).getId());
        }

        detailsRepository.saveAllAndFlush(details);
        return modelMapper.map(response, ImportInvoiceResponse.class);
    }

}
