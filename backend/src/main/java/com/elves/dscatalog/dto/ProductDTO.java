package com.elves.dscatalog.dto;

import com.elves.dscatalog.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imgUrl;

    private Instant date;

    private Set<CategoryDTO> categories = new HashSet<>();


    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();
        date=entity.getDate();
        entity.getCategories().forEach(x -> categories.add(new CategoryDTO(x)));

    }

}
