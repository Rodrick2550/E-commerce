package com.school.ecommerce.controllers.dtos.requests;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CreateOrderItemRequest {

    @NotNull
    private Integer quantity;

    @NotNull
    private Long productId;

}
