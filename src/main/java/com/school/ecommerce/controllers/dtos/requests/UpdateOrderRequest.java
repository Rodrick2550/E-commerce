package com.school.ecommerce.controllers.dtos.requests;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class UpdateOrderRequest {

    @NotNull
    private Long orderStatusId;

}
