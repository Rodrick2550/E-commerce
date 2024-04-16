package com.school.ecommerce.services.interfaces;

import com.school.ecommerce.controllers.dtos.requests.UpdateOrderRequest;
import com.school.ecommerce.controllers.dtos.responses.BaseResponse;
import com.school.ecommerce.entities.Order;

public interface IOrderService {

    BaseResponse list();

    BaseResponse create();

    BaseResponse get(Long id);

    BaseResponse update(Long id, UpdateOrderRequest request);

    BaseResponse delete(Long id);

    BaseResponse getOrderByUserId(Long id);

    Order findOneAndEnsureExistById(Long id);

    Order findOneAndEnsureExistByOrderStatus_NameAndUser_Id(String name, Long userId);

    BaseResponse getDeliveredAndInProgressOrderByUserId(Long userId);

    Order findOneByUserIdOrCreate(Long id);
}
