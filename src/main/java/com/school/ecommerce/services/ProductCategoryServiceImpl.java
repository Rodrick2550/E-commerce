package com.school.ecommerce.services;

import com.school.ecommerce.controllers.dtos.responses.BaseResponse;
import com.school.ecommerce.entities.Category;
import com.school.ecommerce.entities.Product;
import com.school.ecommerce.entities.pivots.ProductCategory;
import com.school.ecommerce.entities.projections.CategoryProjection;
import com.school.ecommerce.entities.projections.ProductProjection;
import com.school.ecommerce.repositories.IProductCategoryRepository;
import com.school.ecommerce.services.interfaces.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements IProductCategoryService {

    @Autowired
    private IProductCategoryRepository repository;

    @Override
    public ProductCategory create(Product product, Category category) {

        if(repository.existsByProductIdAndCategoryId(product.getId(), category.getId())){
            return repository.findByProductIdAndCategoryId(product.getId(), category.getId());
        }

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProduct(product);
        productCategory.setCategory(category);
        return repository.save(productCategory);
    }

    @Override
    public BaseResponse listAllProductsByCategoryName(String categoryName) {

        List<ProductProjection> productProjections = repository.listAllProductsByCategoryName(categoryName);
        List<Product> products = productProjections.stream()
                .map(this::from)
                .collect(Collectors.toList());

        return BaseResponse.builder()
                .data(products)
                .message("Products found correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public BaseResponse listAllCategoriesByProductId(Long productId) {

        List<CategoryProjection> categoryProjections = repository.listAllCategoriesByProductId(productId);
        List<Category> categories = categoryProjections.stream()
                .map(this::toCategoryFrom)
                .collect(Collectors.toList());

        return BaseResponse.builder()
                .data(categories)
                .message("Categories found correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    private Product from(ProductProjection productProjection) {
        Product product = new Product();
        product.setId(productProjection.getId());
        product.setTitle(productProjection.getTitle());
        product.setDescription(productProjection.getDescription());
        product.setImageUrl(productProjection.getImage_url());
        product.setStock(productProjection.getStock());
        product.setPrice(productProjection.getPrice());
        product.setUser(productProjection.getUser());

        return product;
    }

    private Category toCategoryFrom(CategoryProjection categoryProjection) {
        Category category = new Category();
        category.setId(categoryProjection.getId());
        category.setName(categoryProjection.getName());
        category.setProductCategories(categoryProjection.getProductCategories());
        return category;
    }
}
