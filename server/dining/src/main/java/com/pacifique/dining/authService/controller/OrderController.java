package com.pacifique.dining.authService.controller;

import com.pacifique.dining.authService.entity.OrderStatus;
import com.pacifique.dining.authService.repository.OrderRepository;
import com.pacifique.dining.authService.repository.UserRepository;
import com.pacifique.dining.authService.service.JWTService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pacifique.dining.authService.entity.User;
import com.pacifique.dining.authService.entity.Order;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final JWTService jwtService;

    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrders(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        User user = jwtService.getAuthenticatedUser(authorizationHeader);
        System.out.println(user);
        return ResponseEntity.ok(orderRepository.findAllByCustomerId(user.getId()));
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(
            @Valid @RequestBody Order order,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        User user = jwtService.getAuthenticatedUser(authorizationHeader);
        order.setCustomerId(user.getId());
        Order createdOrder = orderRepository.save(order);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        User user = jwtService.getAuthenticatedUser(authorizationHeader);
        return orderRepository.findOrderByIdAndCustomerId(id, user.getId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Order> updateOrder(
            @Valid @RequestBody Order order,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        User user = jwtService.getAuthenticatedUser(authorizationHeader);

        if (!order.getCustomerId().equals(user)) {
            throw new RuntimeException("Unauthorized to update this order");
        }

        Order updatedOrder = orderRepository.save(order);
        return ResponseEntity.ok(updatedOrder);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<Void> cancelOrder(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        User user = jwtService.getAuthenticatedUser(authorizationHeader);
        Order order = orderRepository.findOrderByIdAndCustomerId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Order not found or unauthorized"));

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        return ResponseEntity.noContent().build();
    }
}
