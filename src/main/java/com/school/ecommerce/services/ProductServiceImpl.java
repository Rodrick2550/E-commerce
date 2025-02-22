package com.school.ecommerce.services;

import com.school.ecommerce.controllers.dtos.requests.CreateProductRequest;
import com.school.ecommerce.controllers.dtos.requests.UpdateProductRequest;
import com.school.ecommerce.controllers.dtos.responses.BaseResponse;
import com.school.ecommerce.controllers.exceptions.ObjectNotFoundException;
import com.school.ecommerce.entities.*;
import com.school.ecommerce.entities.pivots.ProductCategory;
import com.school.ecommerce.repositories.IProductRepository;
import com.school.ecommerce.security.UserDetailsImpl;
import com.school.ecommerce.services.interfaces.ICategoryService;
import com.school.ecommerce.services.interfaces.IProductCategoryService;
import com.school.ecommerce.services.interfaces.IProductService;
import com.school.ecommerce.services.interfaces.IProductStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepository repository;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductCategoryService productCategoryService;

    @Autowired
    private IProductStatusService productStatusService;

    private static UserDetailsImpl getUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsImpl) authentication.getPrincipal();
    }

    @Override
    public BaseResponse get(Long id) {
        Product product = findOneAndEnsureExists(id);
        return BaseResponse.builder()
                .data(product)
                .message("Product found correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public BaseResponse list(String keyword) {
        List<Product> products;

        if (keyword == null)
            products = repository.findAll();
        else products = repository.findAllByTitleContainsIgnoreCaseOrDescriptionContainsIgnoreCase(keyword, keyword);

        return BaseResponse.builder()
                .data(products)
                .message("Products found correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public BaseResponse create(CreateProductRequest request) {

        User user = getUserAuthenticated().getUser();

        Product product = repository.save(from(request, user));

        if (request.getCategoryIds() != null)
            setProductCategoriesListToProduct(request.getCategoryIds(), product);

        return BaseResponse.builder()
                .data(product)
                .message("Product created correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.CREATED).build();
    }

    @Override
    public BaseResponse update(Long id, UpdateProductRequest request) {

        Product product = findOneAndEnsureExists(id);

        product = update(product, request);

        return BaseResponse.builder()
                .data(product)
                .message("Product updated correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    public Product findOneAndEnsureExists(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Product not found"));
    }

    @Override
    public void updateStock(Product product, Integer newStock) {
        product.setStock(newStock);
        repository.save(product);
    }

    private Product from(CreateProductRequest request, User user) {
        Product product = new Product();
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setStock(request.getStock());
        product.setPrice(request.getPrice());
        product.setUser(user);
        product.setImageUrl(request.getImageUrl());

        ProductStatus productStatus = productStatusService.findOneAndEnsureExistById(request.getProductStatusId());
        product.setProductStatus(productStatus);

        return product;
    }

    private void setProductCategoriesListToProduct(List<Long> productCategoryIds, Product product) {
        List<ProductCategory> tempProductCategoryList = new ArrayList<>();

        for (Long categoryId : productCategoryIds) {
            Category category = categoryService.findOneAndEnsureExistById(categoryId);
            ProductCategory newCategory = productCategoryService.create(product, category);
            tempProductCategoryList.add(newCategory);
        }

        product.setProductCategories(tempProductCategoryList);

        repository.save(product);
    }

    private Product update(Product product, UpdateProductRequest request) {
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setStock(request.getStock());
        product.setPrice(request.getPrice());
        product.setImageUrl(request.getImageUrl());

        ProductStatus productStatus = productStatusService.findOneAndEnsureExistById(request.getProductStatusId());
        product.setProductStatus(productStatus);


        if (request.getCategoryIds() != null) {
            setProductCategoriesListToProduct(request.getCategoryIds(), product);
        }

        repository.save(product);

        return product;
    }


}
