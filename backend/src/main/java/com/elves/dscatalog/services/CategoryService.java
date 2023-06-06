package com.elves.dscatalog.services;

import com.elves.dscatalog.dto.CategoryDTO;
import com.elves.dscatalog.entities.Category;
import com.elves.dscatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){
        List<CategoryDTO> result = repository.findAll().stream().map(x -> new CategoryDTO(x)).toList();

        return result;
    }
}
