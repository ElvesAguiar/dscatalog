package com.elves.dscatalog.services;

import com.elves.dscatalog.dto.CategoryDTO;
import com.elves.dscatalog.dto.ProductDTO;
import com.elves.dscatalog.entities.Category;
import com.elves.dscatalog.entities.Product;
import com.elves.dscatalog.repositories.ProductRepository;
import com.elves.dscatalog.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Service
public class ProductService {
    @Autowired
    ProductRepository repository;

    public static final String ENF ="Entity not found";

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        Page<Product> page = repository.findAll((pageable));

        return page.map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        if (repository.findById(id).isEmpty()) throw new EntityNotFoundException(ENF);

        return new ProductDTO(repository.findById(id).get());
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        productToDTO(entity, dto);
        return new ProductDTO(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        if (repository.findById(id).isEmpty()) throw new EntityNotFoundException(ENF);

        repository.deleteById(id);

    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {

        if (repository.findById(id).isEmpty()) throw new EntityNotFoundException(ENF);

        Product entity = repository.getReferenceById(id);

        productToDTO(entity, dto);

        return new ProductDTO(entity);
    }

    public void productToDTO(Product entity,ProductDTO dto) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        entity.setDate(dto.getDate());

        entity.getCategories().clear();
        dto.getCategories().forEach(x -> {
            Category cat = new Category();
            cat.setId(x.getId());
            entity.getCategories().add(cat);
        });
    }

}
