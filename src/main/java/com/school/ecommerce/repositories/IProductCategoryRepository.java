package com.school.ecommerce.repositories;

import com.school.ecommerce.entities.pivots.ProductCategory;
import com.school.ecommerce.entities.projections.CategoryProjection;
import com.school.ecommerce.entities.projections.ProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query(value = "select products.*  from products_categories " +
            "inner join products on products_categories.product_id = products.id " +
            "inner join categories on products_categories.category_id = categories.id " +
            "where categories.name = :categoryName", nativeQuery = true)
    List<ProductProjection> listAllProductsByCategoryName(String categoryName);


    @Query(value = "select categories.* from products_categories " +
            "inner join categories on products_categories.category_id = categories.id " +
            "inner join products on products_categories.product_id = products.id " +
            "where products_categories.product_id = :productId", nativeQuery = true)
    List<CategoryProjection> listAllCategoriesByProductId(Long productId);

    boolean existsByProductIdAndCategoryId(Long productId, Long categoryId);

    ProductCategory findByProductIdAndCategoryId(Long productId, Long categoryId);

}
