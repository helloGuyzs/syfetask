package com.helloguyzs.syfetask.services;


import com.helloguyzs.syfetask.dto.category.GetCategoriesResponse;
import com.helloguyzs.syfetask.dto.category.NewCategoryRequest;
import com.helloguyzs.syfetask.models.Category;
import com.helloguyzs.syfetask.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepo repo;

    public List<GetCategoriesResponse> getAllCategories() {


        List<Category> categories= repo.findAll();

        List<GetCategoriesResponse> response = categories.stream()
                .map(category -> new GetCategoriesResponse(
                        category.getName(),
                        category.getType(),
                        category.isCustom()))
                .toList();

        return  response;
    }

    public String addCategory(NewCategoryRequest newCategoryRequestDTO) {

        Category  newCategory = new Category();

        newCategory.setUserId(1);
        newCategory.setName(newCategoryRequestDTO.getName());
        newCategory.setType(newCategoryRequestDTO.getType());
        newCategory.setCustom(true);

        repo.save(newCategory);


        return "Category added successfully";
    }

    public void deleteCategory(String name) {


    }
}
