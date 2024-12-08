package com.pacifique.dining.authService.service;

import com.pacifique.dining.authService.entity.Ingredient;
import com.pacifique.dining.authService.repository.IngredientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public Ingredient updateIngredient(Long id, Ingredient ingredient) {
        return ingredientRepository.findById(id)
                .map(existingIngredient -> {
                    existingIngredient.setName(ingredient.getName());
                    existingIngredient.setDescription(ingredient.getDescription());
                    existingIngredient.setAllergens(ingredient.getAllergens());
                    existingIngredient.setUnit(ingredient.getUnit());
                    existingIngredient.setCalories(ingredient.getCalories());
                    existingIngredient.setHalal(ingredient.isHalal());
                    existingIngredient.setVegan(ingredient.isVegan());
                    existingIngredient.setGlutenFree(ingredient.isGlutenFree());
                    return ingredientRepository.save(existingIngredient);
                })
                .orElseThrow(() -> new EntityNotFoundException("Ingredient not found with id: " + id));
    }
}


