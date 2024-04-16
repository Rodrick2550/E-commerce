package com.school.ecommerce.controllers.dtos.requests;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class CreateOrderStatusRequest {

    @NotNull
    private Long id;

    @NotBlank
    private String name;
}
