package com.school.ecommerce.entities.projections;

import com.school.ecommerce.entities.pivots.UserRole;

import java.util.List;

public interface RoleProjection {

    Long getId();

    String getName();

    List<UserRole> getUserRoles();
}
