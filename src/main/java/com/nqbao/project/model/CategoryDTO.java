package com.nqbao.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private int id;
    private String name;
    private int parentCategoryId;
    private List<ProductDTO> listProduct=new ArrayList<>();

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.parentCategoryId = category.getParent_category()!=null?category.getParent_category().getId():0;
        this.listProduct = category.getListProduct().stream().map(ProductDTO::new).collect(Collectors.toList());
    }

}
