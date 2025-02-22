package com.school.ecommerce.services;

import com.school.ecommerce.controllers.dtos.requests.CreateCategoryRequest;
import com.school.ecommerce.controllers.dtos.requests.UpdateCategoryRequest;
import com.school.ecommerce.controllers.dtos.responses.BaseResponse;
import com.school.ecommerce.controllers.exceptions.ObjectNotFoundException;
import com.school.ecommerce.controllers.exceptions.UniqueConstraintViolationException;
import com.school.ecommerce.entities.Category;
import com.school.ecommerce.repositories.ICategoryRepository;
import com.school.ecommerce.services.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private ICategoryRepository repository;

    @Override
    public BaseResponse list() {
        List<Category> categories = repository.findAll();

        return BaseResponse.builder()
                .data(categories)
                .message("Categories found")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public BaseResponse get(Long id) {
        Category category = findOneAndEnsureExistById(id);
        return BaseResponse.builder()
                .data(category)
                .message("Category found")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public BaseResponse create(CreateCategoryRequest request) {

        if (repository.existsByName(request.getName()))
            throw new UniqueConstraintViolationException("Name is already in use");

        Category category = from(request);
        return BaseResponse.builder()
                .data(repository.save(category))
                .message("Category created correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.CREATED).build();
    }

    @Override
    public BaseResponse update(Long id, UpdateCategoryRequest request) {
        Category category = findOneAndEnsureExistById(id);
        category = update(category, request);

        return BaseResponse.builder()
                .data(repository.save(category))
                .message("Category updated correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public BaseResponse delete(Long id) {
        Category category = findOneAndEnsureExistById(id);
        repository.delete(category);

        return BaseResponse.builder()
                .data(Collections.EMPTY_LIST)
                .message("Category deleted correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public Category findOneAndEnsureExistById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Category not found"));
    }

    @Override
    public Category findOneAndEnsureExistByName(String name) {
        return repository.findByName(name)
                .orElseThrow(()-> new ObjectNotFoundException("Category not found"));
    }

    private Category from(CreateCategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());

        return category;
    }

    private Category update(Category category, UpdateCategoryRequest request) {
        category.setName(request.getName());
        return category;
    }
}
