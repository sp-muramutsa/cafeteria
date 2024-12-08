package com.pacifique.dining.authService.controller;

import com.pacifique.dining.authService.entity.Item;
import com.pacifique.dining.authService.repository.ItemRepository;
import com.pacifique.dining.authService.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/items")
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;

    @PostMapping("/create")
    public ResponseEntity<Item> createItem(@Valid @RequestBody Item item) {
        Item createdItem = itemRepository.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        return itemRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @Valid @RequestBody Item item) {
        Item updatedItem = itemService.updateItem(id, item);
        return ResponseEntity.ok(updatedItem);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/")
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(itemRepository.findAll());
    }


    @PutMapping("/update")
    public ResponseEntity<List<Item>> updateAll(@RequestBody List<Item> items) {
        List<Item> updatedItems = itemService.updateManyItems(items);
        return ResponseEntity.ok(updatedItems);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAllById(@RequestBody List<Long> ids) {
        if (ids.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            itemRepository.deleteAllById(ids);
            return ResponseEntity.noContent().build();
        }
    }
}
