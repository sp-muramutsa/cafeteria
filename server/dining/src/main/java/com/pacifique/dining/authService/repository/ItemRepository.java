package com.pacifique.dining.authService.repository;

import com.pacifique.dining.authService.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}

