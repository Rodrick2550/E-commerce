package com.school.ecommerce.entities.projections;

import com.school.ecommerce.entities.pivots.ProductCategory;

import java.util.List;

public interface CategoryProjection {

    Long getId();

    String getName();

    List<ProductCategory> getProductCategories();

}
