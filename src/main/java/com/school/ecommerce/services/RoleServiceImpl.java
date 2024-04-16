package com.school.ecommerce.services;

import com.school.ecommerce.controllers.dtos.responses.BaseResponse;
import com.school.ecommerce.controllers.exceptions.ObjectNotFoundException;
import com.school.ecommerce.entities.Role;
import com.school.ecommerce.repositories.IRoleRepository;
import com.school.ecommerce.services.interfaces.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleRepository repository;

    @Override
    public BaseResponse list() {
        List<Role> roles = repository.findAll();

        return BaseResponse.builder()
                .data(roles)
                .message("Roles found correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public Role findOneAndEnsureExistById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User Role not found"));
    }

    @Override
    public Role findOneAndEnsureExistByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new ObjectNotFoundException("User Role not found"));
    }

}
