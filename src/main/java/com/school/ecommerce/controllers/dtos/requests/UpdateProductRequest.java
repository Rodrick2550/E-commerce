package com.school.ecommerce.controllers.dtos.requests;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class UpdateProductRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String imageUrl;

    @NotNull
    private Integer stock;

    @NotNull
    private Float price;

    @NotNull
    private Long productStatusId;

    @NotNull
    private List<@NotNull Long> categoryIds;


}
