package com.school.ecommerce.services.interfaces;

import com.school.ecommerce.controllers.dtos.responses.BaseResponse;
import com.school.ecommerce.entities.Role;

public interface IRoleService {

    BaseResponse list();

    Role findOneAndEnsureExistById(Long id);

    Role findOneAndEnsureExistByName(String name);

}
