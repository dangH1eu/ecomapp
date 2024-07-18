package com.project.ecomapp.service;

import com.project.ecomapp.dto.CategoryDTO;
import com.project.ecomapp.model.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDTO category);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    Category updateCategory(Long categoryId, CategoryDTO category);
    void deleteCategory(Long id);



}
