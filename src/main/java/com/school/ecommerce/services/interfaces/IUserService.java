package com.school.ecommerce.services.interfaces;

import com.school.ecommerce.controllers.dtos.requests.CreateUserRequest;
import com.school.ecommerce.controllers.dtos.requests.UpdateUserRequest;
import com.school.ecommerce.controllers.dtos.responses.BaseResponse;
import com.school.ecommerce.entities.User;

public interface IUserService {
    BaseResponse create(CreateUserRequest request);

    BaseResponse get(Long id);

    BaseResponse update(UpdateUserRequest request, Long id);

    BaseResponse delete(Long id);

    User findOneAndEnsureExistById(Long id);

}
