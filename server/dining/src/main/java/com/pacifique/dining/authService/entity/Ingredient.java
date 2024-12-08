package com.pacifique.dining.authService.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ElementCollection
    @CollectionTable(
            name = "ingredient_allergens",
            joinColumns = @JoinColumn(name = "ingredient_id")
    )
    @Column(name = "allergen")
    private List<String> allergens;

    private String unit;

    private int calories;

    private boolean isHalal;

    private boolean isVegan;

    private boolean isGlutenFree;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

}
