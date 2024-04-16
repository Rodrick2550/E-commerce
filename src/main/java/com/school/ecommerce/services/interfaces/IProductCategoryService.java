package com.school.ecommerce.services.interfaces;

import com.school.ecommerce.controllers.dtos.responses.BaseResponse;
import com.school.ecommerce.entities.Category;
import com.school.ecommerce.entities.Product;
import com.school.ecommerce.entities.pivots.ProductCategory;

public interface IProductCategoryService {

    ProductCategory create(Product product, Category category);

    BaseResponse listAllProductsByCategoryName(String categoryName);

    BaseResponse listAllCategoriesByProductId(Long productId);

}
