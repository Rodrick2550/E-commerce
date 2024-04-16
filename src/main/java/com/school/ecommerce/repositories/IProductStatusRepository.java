package com.school.ecommerce.repositories;

import com.school.ecommerce.entities.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IProductStatusRepository extends JpaRepository<ProductStatus, Long> {

    Optional<ProductStatus> findByName(String name);

    boolean existsByName(String name);
}
