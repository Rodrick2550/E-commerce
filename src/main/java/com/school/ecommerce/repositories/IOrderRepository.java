package com.school.ecommerce.repositories;

import com.school.ecommerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> getOneByOrderStatus_NameAndUser_Id(String name, Long userId);

    List<Order> getAllByOrderStatus_NameAndUser_Id(String name, Long userId);
}
