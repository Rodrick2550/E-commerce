package com.school.ecommerce.repositories;

import com.school.ecommerce.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findById(Long id);

    List<Address> findAllByUserId(Long id);


}
