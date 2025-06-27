package com.helloguyzs.syfetask.controller;


import com.helloguyzs.syfetask.dto.category.DeleteCategoryResponse;
import com.helloguyzs.syfetask.dto.category.GetCategoriesResponse;
import com.helloguyzs.syfetask.dto.category.NewCategoryRequest;
import com.helloguyzs.syfetask.dto.category.NewCategoryResponse;
import com.helloguyzs.syfetask.models.Category;
import com.helloguyzs.syfetask.services.CategoryService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.helloguyzs.syfetask.utils.SessionUtil.getCurrentUserId;

import jakarta.servlet.http.HttpServletRequest; // not HttpServletResponse
import static com.helloguyzs.syfetask.utils.SessionUtil.getCurrentUserId;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public GetCategoriesResponse getALlCategories(HttpServletRequest request) {
        Integer userId = getCurrentUserId(request);
        return categoryService.getAllCategories(userId);
    }

    @PostMapping("/categories")
    public NewCategoryResponse addCategory(@RequestBody @Valid NewCategoryRequest request,
                                           HttpServletRequest httpRequest) {
        Integer userId = getCurrentUserId(httpRequest);
        return categoryService.addCategory(userId, request);
    }

    @DeleteMapping("categories/{name}")
    public DeleteCategoryResponse deleteCategory(@PathVariable String name,
                                                 HttpServletRequest httpRequest) {
        Integer userId = getCurrentUserId(httpRequest);
        return categoryService.deleteCategory(userId, name);
    }
}
