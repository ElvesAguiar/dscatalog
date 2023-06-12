package com.elves.dscatalog.repositories;

import com.elves.dscatalog.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;



    @Test
    void deleteShouldDeleteObjectWhenIdExists() {
        repository.deleteById(1L);

        Optional<Product> result = repository.findById(1L);

        Assertions.assertFalse(result.isPresent());
    }


}
