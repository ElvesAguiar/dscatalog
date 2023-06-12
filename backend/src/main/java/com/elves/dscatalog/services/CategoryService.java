package com.elves.dscatalog.services;

import com.elves.dscatalog.dto.CategoryDTO;
import com.elves.dscatalog.model.Category;
import com.elves.dscatalog.repositories.CategoryRepository;
import com.elves.dscatalog.exceptions.DomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CategoryService {
    @Autowired
    CategoryRepository repository;
    public static final String ENF ="Entity not found";

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable) {
        Page<Category> page = repository.findAll((pageable));

        return page.map(x -> new CategoryDTO(x));
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        if (repository.findById(id).isEmpty()) throw new DomainException(ENF);

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
        if (repository.findById(id).isEmpty()) throw new DomainException(ENF);

        repository.deleteById(id);

    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {

        if (repository.findById(id).isEmpty()) throw new DomainException(ENF);

        Category cat = repository.getReferenceById(id);

        cat.setName(dto.getName());

        return new CategoryDTO(cat);
    }

}
