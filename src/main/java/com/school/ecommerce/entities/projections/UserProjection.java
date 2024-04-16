package com.school.ecommerce.entities.projections;

import com.school.ecommerce.entities.Address;
import com.school.ecommerce.entities.Product;
import com.school.ecommerce.entities.pivots.UserRole;

import java.util.Date;
import java.util.List;

public interface UserProjection {

    Long getId();

    String getEmail();

    String getPassword();

    String getFirstName();

    String getLastName();

    Date getDateOfBirth();

    List<UserRole> getUserRoles();

    List<Product> getProducts();

    List<Address> getAddresses();
}
