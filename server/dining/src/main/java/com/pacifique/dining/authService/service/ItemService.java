package com.pacifique.dining.authService.service;

import com.pacifique.dining.authService.entity.Item;
import com.pacifique.dining.authService.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;


    public Item updateItem(Long id, Item item) {
        return itemRepository.findById(id)
                .map(existingItem -> {
                    existingItem.setName(item.getName());
                    existingItem.setDescription(item.getDescription());
                    existingItem.setPrice(item.getPrice());
                    existingItem.setAvailable(item.isAvailable());
                    existingItem.setIngredientsIds(item.getIngredientsIds());
                    existingItem.setType(item.getType());
                    return itemRepository.save(existingItem);
                })
                .orElseThrow(() -> new EntityNotFoundException("Item not found with id: " + id));
    }


    public List<Item> updateManyItems(List<Item> items) {
        if (items.isEmpty()) {
            throw new IllegalArgumentException("Item list cannot be empty");
        }

        return items.stream()
                .map(item -> updateItem(item.getId(), item))
                .collect(Collectors.toList());
    }
}
