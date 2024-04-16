package com.school.ecommerce.services;

import com.school.ecommerce.controllers.dtos.responses.BaseResponse;
import com.school.ecommerce.controllers.exceptions.ObjectNotFoundException;
import com.school.ecommerce.entities.ProductStatus;
import com.school.ecommerce.repositories.IProductStatusRepository;
import com.school.ecommerce.services.interfaces.IProductStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductStatusServiceImpl implements IProductStatusService {

    @Autowired
    private IProductStatusRepository repository;

    @Override
    public BaseResponse list() {
        List<ProductStatus> productStatus = repository.findAll();

        return BaseResponse.builder()
                .data(productStatus)
                .message("Product status found correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public ProductStatus findOneAndEnsureExistById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Product status not found"));
    }

}
