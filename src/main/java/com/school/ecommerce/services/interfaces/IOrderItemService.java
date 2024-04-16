package com.school.ecommerce.services.interfaces;

import com.school.ecommerce.controllers.dtos.requests.CreateOrderItemRequest;
import com.school.ecommerce.controllers.dtos.requests.UpdateOrderItemRequest;
import com.school.ecommerce.controllers.dtos.responses.BaseResponse;

public interface IOrderItemService {

    BaseResponse create(CreateOrderItemRequest request);

    BaseResponse get(Long id);

    BaseResponse update(Long id, UpdateOrderItemRequest request);

    BaseResponse delete(Long id);
}
