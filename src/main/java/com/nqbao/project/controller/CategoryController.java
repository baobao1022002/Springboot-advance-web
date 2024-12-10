package com.nqbao.project.controller;

import com.nqbao.project.model.Category;
import com.nqbao.project.model.CategoryDTO;
import com.nqbao.project.service.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/categories")
public class CategoryController extends GenericController<Category, Integer, CategoryDTO> {

    public CategoryController(CategoryService categoryService) {
        super(categoryService);
    }


}
