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
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public List<GetCategoriesResponse> getALlCategories() {


        return categoryService.getAllCategories(1);
    }


    @PostMapping("/categories")
    public String addCategory(@RequestBody @Valid NewCategoryRequest request) {

        System.out.println("Adding category: " + request.getName());
        return categoryService.addCategory(request);
    }

    @DeleteMapping("categories/{name}")
    public String deleteCategory(@PathVariable String name) {

        System.out.println(name);
        return categoryService.deleteCategory(name);
    }

}
