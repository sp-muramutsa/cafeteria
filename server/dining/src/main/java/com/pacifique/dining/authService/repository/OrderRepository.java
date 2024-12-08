package com.pacifique.dining.authService.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.pacifique.dining.authService.entity.Order;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findOrderByIdAndCustomerId(Long id, UUID customerId);

    List<Order> findAllByCustomerId(UUID customerId);
}
