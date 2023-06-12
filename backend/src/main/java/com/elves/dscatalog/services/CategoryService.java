package com.elves.dscatalog.services;

import com.elves.dscatalog.dto.CategoryDTO;
import com.elves.dscatalog.exceptions.DomainException;
import com.elves.dscatalog.model.Category;
import com.elves.dscatalog.repositories.CategoryRepository;
import com.elves.dscatalog.utils.DomainModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CategoryService {
    @Autowired
    CategoryRepository repository;

    ModelMapper modelMapper = new DomainModelMapper();
    public static final String ENF = "Entity not found";

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable) {
        Page<Category> page = repository.findAll((pageable));

        return page.map(x -> modelMapper.map(repository.findById(x.getId()), CategoryDTO.class));
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        if (repository.findById(id).isEmpty()) throw new DomainException(ENF);

        CategoryDTO dto = modelMapper.map(repository.findById(id), CategoryDTO.class);

        return dto;
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        Category entity = new Category();

        entity.setName(dto.getName());

        repository.save(entity);
        CategoryDTO categoryDTO = modelMapper.map(repository.findById(entity.getId()), CategoryDTO.class);
        return categoryDTO;
    }

    @Transactional
    public void delete(Long id) {
        if (repository.findById(id).isEmpty()) throw new DomainException(ENF);

        repository.deleteById(id);

    }

    //
    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {

        if (repository.findById(id).isEmpty()) throw new DomainException(ENF);

        Category cat = repository.getReferenceById(id);
        cat.setName(dto.getName());

        CategoryDTO categoryDTO = modelMapper.map(repository.findById(cat.getId()), CategoryDTO.class);

        return categoryDTO;
    }

}
