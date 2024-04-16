package com.school.ecommerce.controllers.dtos.requests;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CreateCategoryRequest {

    @NotBlank
    private String name;

}
