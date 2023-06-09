package com.elves.dscatalog.dto;

import com.elves.dscatalog.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;


    public CategoryDTO(Category entity) {
        id = entity.getId();
        name = entity.getName();
    }


}
