package com.school.ecommerce.services.interfaces;

import com.school.ecommerce.controllers.dtos.responses.BaseResponse;
import com.school.ecommerce.entities.ProductStatus;

public interface IProductStatusService {

    BaseResponse list();

    ProductStatus findOneAndEnsureExistById(Long id);

}
