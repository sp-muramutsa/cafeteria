package com.pacifique.dining.authService.controller;

import com.pacifique.dining.authService.entity.Ingredient;
import com.pacifique.dining.authService.repository.IngredientRepository;
import com.pacifique.dining.authService.service.IngredientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/ingredients")
public class IngredientController {

    private final IngredientRepository ingredientRepository;
    private final IngredientService ingredientService;

    @PostMapping("/create")
    public ResponseEntity<Ingredient> createIngredient(@Valid @RequestBody Ingredient ingredient) {
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedIngredient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable Long id) {
        return ingredientRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable Long id, @Valid @RequestBody Ingredient ingredient) {
        Ingredient updatedIngredient = ingredientService.updateIngredient(id, ingredient);
        return ResponseEntity.ok(updatedIngredient);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        if (ingredientRepository.existsById(id)) {
            ingredientRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(ingredientRepository.findAll());
    }

    @PutMapping("/update")
    public ResponseEntity<List<Ingredient>> updateAllById(@RequestBody List<Ingredient> ingredients) {

        if (ingredients.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<Long> ids = ingredients.stream().map(Ingredient::getId).collect(Collectors.toList());
        List<Ingredient> existingIngredients = ingredientRepository.findAllById(ids);

        if (existingIngredients.size() != ingredients.size()) {
            return ResponseEntity.badRequest().build();
        }

        List<Ingredient> updatedIngredients = ingredients.stream()
                .map(ingredient -> ingredientService.updateIngredient(ingredient.getId(), ingredient))
                .collect(Collectors.toList());

        return ResponseEntity.ok(updatedIngredients);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAllById(@RequestBody List<Long> ids) {
        if (ids.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        ingredientRepository.deleteAllById(ids);
        return ResponseEntity.noContent().build();
    }
}
