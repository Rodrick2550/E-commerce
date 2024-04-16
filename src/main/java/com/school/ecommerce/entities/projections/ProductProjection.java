package com.school.ecommerce.entities.projections;

import com.school.ecommerce.entities.User;

public interface ProductProjection {

    Long getId();

    String getTitle();

    String getDescription();

    String getImage_url();

    Integer getStock();

    Float getPrice();

    User getUser();

}
