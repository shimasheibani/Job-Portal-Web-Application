package org.springBootAngular.service;

import org.springBootAngular.dto.CategoryDTO;
import org.springBootAngular.dto.Response;

public interface CategoryService {
    Response createCategory(CategoryDTO categoryDTO);
    Response getAllCategories();
    Response getCategoryById(Long id);
    Response updateCategory(Long id,CategoryDTO categoryDTO);
    Response deleteCategory(Long id);
}
