package com.helloguyzs.syfetask.controller;


import com.helloguyzs.syfetask.dto.category.DeleteCategoryResponse;
import com.helloguyzs.syfetask.dto.category.GetCategoriesResponse;
import com.helloguyzs.syfetask.dto.category.NewCategoryRequest;
import com.helloguyzs.syfetask.dto.category.NewCategoryResponse;
import com.helloguyzs.syfetask.models.Category;
import com.helloguyzs.syfetask.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public GetCategoriesResponse getALlCategories() {
        Integer userId = 1;
        return categoryService.getAllCategories( userId);
    }


    @PostMapping("/categories")
    public NewCategoryResponse addCategory(@RequestBody @Valid NewCategoryRequest request) {

        Integer userId = 1;
        return categoryService.addCategory(userId , request);
    }

    @DeleteMapping("categories/{name}")
    public DeleteCategoryResponse deleteCategory(@PathVariable String name) {

        Integer userId = 1;
        return categoryService.deleteCategory( userId, name);
    }

}
