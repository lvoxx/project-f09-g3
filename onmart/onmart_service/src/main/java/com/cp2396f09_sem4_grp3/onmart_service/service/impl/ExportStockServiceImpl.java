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

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ExportInvoiceRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ExportInvoiceResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.ExportStockInvoice;
import com.cp2396f09_sem4_grp3.onmart_common.entities.ExportStockInvoiceDetails;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Product;
import com.cp2396f09_sem4_grp3.onmart_common.entities.User;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ExportStockInvoiceDetailsRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ExportStockInvoiceRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ProductRepository;
import com.cp2396f09_sem4_grp3.onmart_service.service.ExportStockService;
import com.cp2396f09_sem4_grp3.onmart_service.service.ProductService;
import com.cp2396f09_sem4_grp3.onmart_service.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ExportStockServiceImpl implements ExportStockService {
    @Value("${app.jpa.max-items-in-page}")
    private int maxItems;

    private final ExportStockInvoiceRepository exportRepository;
    private final ExportStockInvoiceDetailsRepository detailsRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public Page<ExportInvoiceResponse> getAllInvoices(Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, maxItems, Sort.by(sortBy));
        return exportRepository.findAll(paging)
                .map(inv -> modelMapper.map(inv, ExportInvoiceResponse.class));
    }

    @Override
    public ExportInvoiceResponse getInvoiceById(Long id) {
        return modelMapper.map(exportRepository.findById(id), ExportInvoiceResponse.class);
    }

    @Override
    public ExportInvoiceResponse createInvoice(String email, List<ExportInvoiceRequest> request) {
        User user = userService.findByEmail(email);
        ExportStockInvoice invoice = ExportStockInvoice.builder()
                .creatorId(user.getId())
                .creatorName(user.getFirstName() + " " + user.getLastName())
                .build();
        ExportStockInvoice response = exportRepository.saveAndFlush(invoice);
        List<Product> products = productRepository
                .findAllById(
                        request.stream()
                                .map(ExportInvoiceRequest::getProductId)
                                .collect(Collectors.toList()));
        // Sort 2 list
        products.sort((item1, item2) -> item1.getId().compareTo(item2.getId()));
        request.sort((item1, item2) -> item1.getProductId().compareTo(item2.getProductId()));

        // Assign value to details
        java.util.List<ExportStockInvoiceDetails> details = new ArrayList<>();
        for (int i = 0; i < products.size(); ++i) {
            details.add(ExportStockInvoiceDetails.builder()
                    .productId(products.get(i).getId())
                    .productName(products.get(i).getName())
                    .quantity(request.get(i).getQuantity())
                    .exportStockInvoice(response)
                    .build());
            productService.exportProduct(request.get(i).getQuantity(), products.get(i).getId());
        }

        detailsRepository.saveAllAndFlush(details);
        return modelMapper.map(response, ExportInvoiceResponse.class);
    }
}
