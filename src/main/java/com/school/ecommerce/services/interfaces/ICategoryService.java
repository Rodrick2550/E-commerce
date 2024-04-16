package com.school.ecommerce.services.interfaces;

import com.school.ecommerce.controllers.dtos.requests.CreateCategoryRequest;
import com.school.ecommerce.controllers.dtos.requests.UpdateCategoryRequest;
import com.school.ecommerce.controllers.dtos.responses.BaseResponse;
import com.school.ecommerce.entities.Category;

public interface ICategoryService {

    BaseResponse list();

    BaseResponse get(Long id);

    BaseResponse create(CreateCategoryRequest request);

    BaseResponse update(Long id, UpdateCategoryRequest request);

    BaseResponse delete(Long id);

    Category findOneAndEnsureExistById(Long id);

    Category findOneAndEnsureExistByName(String name);

}
