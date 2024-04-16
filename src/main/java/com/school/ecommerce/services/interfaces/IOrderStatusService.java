package com.school.ecommerce.services.interfaces;

import com.school.ecommerce.controllers.dtos.responses.BaseResponse;
import com.school.ecommerce.entities.OrderStatus;

public interface IOrderStatusService {

    BaseResponse list();

    OrderStatus findOneAndEnsureExistById(Long id);

    OrderStatus findOneAndEnsureExistByName(String name);
}
