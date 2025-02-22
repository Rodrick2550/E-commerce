package com.school.ecommerce.services;

import com.school.ecommerce.controllers.dtos.requests.CreateUserRequest;
import com.school.ecommerce.controllers.dtos.requests.UpdateUserRequest;
import com.school.ecommerce.controllers.dtos.responses.BaseResponse;
import com.school.ecommerce.controllers.dtos.responses.UserResponse;
import com.school.ecommerce.controllers.exceptions.AccessDeniedException;
import com.school.ecommerce.controllers.exceptions.ObjectNotFoundException;
import com.school.ecommerce.controllers.exceptions.UniqueConstraintViolationException;
import com.school.ecommerce.entities.Role;
import com.school.ecommerce.entities.User;
import com.school.ecommerce.entities.pivots.UserRole;
import com.school.ecommerce.repositories.IUserRepository;
import com.school.ecommerce.security.UserDetailsImpl;
import com.school.ecommerce.services.interfaces.IRoleService;
import com.school.ecommerce.services.interfaces.IUserRoleService;
import com.school.ecommerce.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository repository;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserRoleService userRoleService;

    private static UserDetailsImpl getUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsImpl) authentication.getPrincipal();
    }

    @Override
    public BaseResponse get(Long id) {

        UserDetailsImpl userDetails = getUserAuthenticated();

        if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
                && !userDetails.getUser().getId().equals(id))
            throw new AccessDeniedException();

        User user = findOneAndEnsureExistById(id);

        return BaseResponse.builder()
                .data(from(user))
                .message("User found")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse create(CreateUserRequest request) {

        if (repository.existsByEmail(request.getEmail()))
            throw new UniqueConstraintViolationException("Email is already in use");

        User user = repository.save(from(request));

        Role defaultRole = roleService.findOneAndEnsureExistByName("ROLE_USER");

        user = addRoleToUser(defaultRole, user);

        return BaseResponse.builder()
                .data(from(user))
                .message("User created correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    @Override
    public BaseResponse update(UpdateUserRequest request, Long id) {

        User userAuthenticated = getUserAuthenticated().getUser();

        if (!userAuthenticated.getId().equals(id))
            throw new AccessDeniedException();

        userAuthenticated = update(userAuthenticated, request);

        return BaseResponse.builder()
                .data(from(userAuthenticated))
                .message("User updated correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse delete(Long id) {

        User user = getUserAuthenticated().getUser();

        if (!user.getId().equals(id))
            throw new AccessDeniedException();

        if (!repository.existsById(id))
            throw new ObjectNotFoundException("User not found");

        repository.deleteById(id);

        return BaseResponse.builder()
                .data(Collections.EMPTY_LIST)
                .message("User deleted correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.NO_CONTENT)
                .build();
    }

    @Override
    public User findOneAndEnsureExistById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User not found"));
    }

    private User addRoleToUser(Role defaultRole, User user) {
        UserRole userRole = userRoleService.create(user, defaultRole);

        List<UserRole> userRoleList = new ArrayList<>();
        userRoleList.add(userRole);

        user.setUserRoles(userRoleList);

        user = repository.save(user);

        return user;
    }

    private User update(User user, UpdateUserRequest request) {
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setPassword(encodePassword(request.getPassword()));

        repository.save(user);

        return user;
    }

    private User from(CreateUserRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setPassword(encodePassword(request.getPassword()));

        return user;
    }

    private static String encodePassword(String request) {
        return new BCryptPasswordEncoder().encode(request);
    }

    private UserResponse from(User user) {
        UserResponse userResponse = new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setDateOfBirth(user.getDateOfBirth());

        return userResponse;
    }
}
