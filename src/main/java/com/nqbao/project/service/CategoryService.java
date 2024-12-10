package com.nqbao.project.service;

import com.nqbao.project.model.Category;
import com.nqbao.project.model.CategoryDTO;
import com.nqbao.project.repository.CategoryRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "categoryService")
public class CategoryService extends GenericService<Category, Integer, CategoryDTO> {

    public CategoryService(CategoryRepository categoryRepository) {
        super(categoryRepository,"category", CategoryDTO::new, Category::new);
    }

}
