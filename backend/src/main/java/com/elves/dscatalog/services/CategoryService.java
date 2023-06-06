package com.elves.dscatalog.services;

import com.elves.dscatalog.dto.CategoryDTO;
import com.elves.dscatalog.entities.Category;
import com.elves.dscatalog.repositories.CategoryRepository;
import com.elves.dscatalog.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        return repository.findAll().stream().map(CategoryDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        if (repository.findById(id).isEmpty()) throw new EntityNotFoundException("Entity not found");

        return new CategoryDTO(repository.findById(id).get());
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        Category entity = new Category();
        entity.setName(dto.getName());
        return new CategoryDTO(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        if (repository.findById(id).isEmpty()) throw new EntityNotFoundException("Entity not found");

        repository.deleteById(id);

    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {

        if (repository.findById(id).isEmpty()) throw new EntityNotFoundException("Entity not found");

        Category cat = repository.getReferenceById(id);

        cat.setName(dto.getName());

        return new CategoryDTO(cat);
    }

}
