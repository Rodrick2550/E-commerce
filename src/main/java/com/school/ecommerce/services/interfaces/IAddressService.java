package com.school.ecommerce.services.interfaces;

import com.school.ecommerce.controllers.dtos.requests.CreateAddressRequest;
import com.school.ecommerce.controllers.dtos.requests.UpdateAddressRequest;
import com.school.ecommerce.controllers.dtos.responses.BaseResponse;
import com.school.ecommerce.entities.Address;


public interface IAddressService {

    BaseResponse create(CreateAddressRequest request);

    BaseResponse get(Long id);

    BaseResponse list(Long userId);

    BaseResponse update(Long id, UpdateAddressRequest request);

    BaseResponse delete(Long id);

    Address findOneAndEnsureExistById(Long id);

}
