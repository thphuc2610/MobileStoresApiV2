package com.example.store.service;

import com.example.store.dao.entity.Category;
import com.example.store.dto.request.CategoryRequest;
import com.example.store.dto.response.CategoryResponse;
import com.example.store.mapper.CategoryMapper;
import com.example.store.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMapper categoryMapper;

    public CategoryResponse addCategory(CategoryRequest request) throws Exception {
        try {
            Category newCategory= categoryRepository.save(
                    Category.builder()
                            .name(request.getName())
                            .build());
            return categoryMapper.mapResult(newCategory);
        } catch (Exception ex) {
            throw new Exception();
        }
    }

    public List<CategoryResponse> getListCategory(){
        try {
            List<Category> categoryList = categoryRepository.findAll();

            List<CategoryResponse> responses = new ArrayList<>();
            categoryList.forEach(item -> {
                responses.add(categoryMapper.mapResult(item));
            });

            return responses;
        }catch (Exception e){
            return new ArrayList<>();
        }
    }
}
