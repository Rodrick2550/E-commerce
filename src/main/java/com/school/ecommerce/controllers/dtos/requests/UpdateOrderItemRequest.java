package com.school.ecommerce.controllers.dtos.requests;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class UpdateOrderItemRequest {

    @NotNull
    private Integer quantity;

}
