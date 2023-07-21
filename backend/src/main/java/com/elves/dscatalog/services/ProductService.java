package com.elves.dscatalog.services;

import com.elves.dscatalog.dto.ProductDTO;
import com.elves.dscatalog.exceptions.DomainException;
import com.elves.dscatalog.model.Category;
import com.elves.dscatalog.model.Product;
import com.elves.dscatalog.projections.ProductProjection;
import com.elves.dscatalog.repositories.ProductRepository;
import com.elves.dscatalog.utils.DomainModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


@Service
public class ProductService {
    @Autowired
    ProductRepository repository;

    ModelMapper modelMapper = new DomainModelMapper();

    public static final String ENF = "Entity not found";

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        Page<Product> page = repository.findAll((pageable));

        return page.map(x -> modelMapper.map(repository.findById(x.getId()), ProductDTO.class));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        if (repository.findById(id).isEmpty()) throw new DomainException(ENF);
        return modelMapper.map(repository.findById(id), ProductDTO.class);
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        productToDTO(entity, dto);
        repository.save(entity);

        return modelMapper.map(repository.findById(entity.getId()), ProductDTO.class);
    }

    @Transactional
    public void delete(Long id) {
        if (repository.findById(id).isEmpty()) throw new DomainException(ENF);

        repository.deleteById(id);

    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {

        if (repository.findById(id).isEmpty()) throw new DomainException(ENF);

        Product entity = repository.getReferenceById(id);

        productToDTO(entity, dto);
        repository.save(entity);
        return modelMapper.map(repository.findById(entity.getId()), ProductDTO.class);
    }

    public void productToDTO(Product entity, ProductDTO dto) {
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

    @Transactional(readOnly = true)
    public Page<ProductDTO> fibdAllPaged(String name, String categoryId, Pageable pageable) {

        String[] vet = categoryId.split(",");
        List<String> list = Arrays.asList(vet);
        List<Long> categoryIds = !"0".equals(categoryId)
                ? list.stream().map(Long::parseLong).toList() : Arrays.asList();


        Page<ProductProjection> page = repository.searchProducts(categoryIds, name, pageable);

        List<Long> productIds = page.map(x -> x.getId()).toList();

        List<Product> result =  repository.searchProductsWithCategories(productIds);
        return page.map(x -> modelMapper.map(repository.findById(x.getId()), ProductDTO.class));

    }
}
