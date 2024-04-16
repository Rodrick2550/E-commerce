package com.school.ecommerce.controllers.dtos.requests;

import lombok.Getter;

@Getter
public class CheckoutRequest {

    Long orderId;

    String cardNumber;
}
