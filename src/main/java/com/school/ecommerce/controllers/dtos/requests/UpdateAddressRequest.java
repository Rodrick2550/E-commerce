package com.school.ecommerce.controllers.dtos.requests;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UpdateAddressRequest {

    @NotBlank
    private String street;

    @NotBlank
    private String zipcode;

    @NotBlank
    private String state;

    @NotBlank
    private String country;
}
