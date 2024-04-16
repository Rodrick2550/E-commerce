package com.school.ecommerce.services.interfaces;

import com.school.ecommerce.controllers.dtos.requests.CheckoutRequest;
import com.school.ecommerce.controllers.dtos.responses.BaseResponse;

public interface ICheckoutService {

    BaseResponse checkout(CheckoutRequest request);
}
