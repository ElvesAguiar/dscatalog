package com.elves.dscatalog.controllers;

import com.elves.dscatalog.dto.CategoryDTO;
import com.elves.dscatalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> findAll(Pageable pageable) {
        Page<CategoryDTO> page = service.findAll(pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        CategoryDTO entity = service.findById(id);
        return ResponseEntity.ok().body(entity);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> findById(@RequestBody CategoryDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO dto) {

        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);

    }

}
