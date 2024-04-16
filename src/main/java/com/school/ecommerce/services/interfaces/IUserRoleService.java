package com.school.ecommerce.services.interfaces;

import com.school.ecommerce.controllers.dtos.responses.BaseResponse;
import com.school.ecommerce.entities.Role;
import com.school.ecommerce.entities.User;
import com.school.ecommerce.entities.pivots.UserRole;

import java.util.List;

public interface IUserRoleService {

    BaseResponse listAllUsersByRoleId(Long id);

    BaseResponse listAllRolesByUserId(Long id);

    List<Role> getAllRolesByUserId(Long id);

    UserRole create(User user, Role role);
}
