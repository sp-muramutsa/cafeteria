package com.pacifique.dining.authService.repository;

import com.pacifique.dining.authService.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
