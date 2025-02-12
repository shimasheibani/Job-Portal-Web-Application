package org.springBootAngular.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.internal.bytebuddy.TypeCache;
import org.springBootAngular.dto.CategoryDTO;
import org.springBootAngular.dto.Response;
import org.springBootAngular.entity.Category;
import org.springBootAngular.exception.NotFoundException;
import org.springBootAngular.repository.CategoryRepository;
import org.springBootAngular.service.CategoryService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class );
        categoryRepository.save(category);
        return Response.builder()
                .status(200)
                .message("Category created")
                .build();
    }

    @Override
    public Response getAllCategories() {
        List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.DESC , "id"));
        List<CategoryDTO> categoryDTOS = modelMapper.map(categories, new TypeToken<List<CategoryDTO>>() {}.getType());
        return Response.builder()
                .status(200)
                .message("All categories")
                .categories(categoryDTOS)
                .build();
    }

    @Override
    public Response getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found"));
        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
        return Response.builder()
                .status(200)
                .message("Category found")
                .category(categoryDTO)
                .build();

    }

    @Override
    public Response updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found"));
        if(categoryDTO.getName() != null){
            category.setName(categoryDTO.getName());
        }
        categoryRepository.save(category);
        return Response.builder()
                .status(200)
                .message("update category successful")
                .build();
    }

    @Override
    public Response deleteCategory(Long id) {
        categoryRepository.findById(id).orElseThrow(()->new NotFoundException("this category id not found"));
        categoryRepository.deleteById(id);
        return Response.builder()
                .status(200)
                .message("category successfully deleted")
                .build();
    }
}
