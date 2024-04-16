package com.school.ecommerce.services.interfaces;

import com.school.ecommerce.controllers.dtos.requests.CreateProductRequest;
import com.school.ecommerce.controllers.dtos.requests.UpdateProductRequest;
import com.school.ecommerce.controllers.dtos.responses.BaseResponse;
import com.school.ecommerce.entities.Product;

public interface IProductService {

    BaseResponse get(Long id);

    BaseResponse list(String keyword);

    BaseResponse create(CreateProductRequest request);

    BaseResponse update(Long id, UpdateProductRequest request);

    Product findOneAndEnsureExists(Long id);

    void updateStock(Product product, Integer newStock);

}
