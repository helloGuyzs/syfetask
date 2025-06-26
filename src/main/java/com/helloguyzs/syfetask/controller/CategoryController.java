package com.helloguyzs.syfetask.controller;


import com.helloguyzs.syfetask.dto.category.GetCategoriesResponse;
import com.helloguyzs.syfetask.dto.category.NewCategoryRequest;
import com.helloguyzs.syfetask.models.Category;
import com.helloguyzs.syfetask.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public List<GetCategoriesResponse> getALlCategories() {


        return categoryService.getAllCategories();
    }


    @PostMapping("/categories")
    public String addCategory(@RequestBody @Valid NewCategoryRequest request) {

        System.out.println("Adding category: " + request.getName());
        categoryService.addCategory(request);

        return "Category added successfully";
    }

    @DeleteMapping("categories/{name}")
    public String deleteCategory(@PathVariable String name) {

        categoryService.deleteCategory(name);
        return "Category deleted successfully";
    }

}
