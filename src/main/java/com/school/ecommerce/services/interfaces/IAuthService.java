package com.school.ecommerce.services.interfaces;

import com.school.ecommerce.controllers.dtos.requests.AuthenticationRequest;
import com.school.ecommerce.controllers.dtos.responses.BaseResponse;

public interface IAuthService {
    BaseResponse authenticate(AuthenticationRequest request);
}
