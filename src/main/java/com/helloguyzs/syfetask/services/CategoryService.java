package com.helloguyzs.syfetask.services;

import com.helloguyzs.syfetask.dto.category.DeleteCategoryResponse;
import com.helloguyzs.syfetask.dto.category.GetCategoriesResponse;
import com.helloguyzs.syfetask.dto.category.NewCategoryRequest;
import com.helloguyzs.syfetask.dto.category.NewCategoryResponse;
import com.helloguyzs.syfetask.enums.CategoryType;
import com.helloguyzs.syfetask.exceptions.ConflictException;
import com.helloguyzs.syfetask.exceptions.ForbiddenException;
import com.helloguyzs.syfetask.exceptions.NotFoundException;
import com.helloguyzs.syfetask.models.Category;
import com.helloguyzs.syfetask.models.Transaction;
import com.helloguyzs.syfetask.repo.CategoryRepo;
import com.helloguyzs.syfetask.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private CategoryRepo repo;

    public void createDefaultCatogaries(Integer userId) {
        repo.saveAll(List.of(
                new Category(null, userId, "Salary", CategoryType.INCOME, false),
                new Category(null, userId, "Food", CategoryType.EXPENSE, false),
                new Category(null, userId, "Rent", CategoryType.EXPENSE, false),
                new Category(null, userId, "Transportation", CategoryType.EXPENSE, false),
                new Category(null, userId, "Entertainment", CategoryType.EXPENSE, false),
                new Category(null, userId, "Healthcare", CategoryType.EXPENSE, false),
                new Category(null, userId, "Utilities", CategoryType.EXPENSE, false)
        ));
    }

    public GetCategoriesResponse getAllCategories(Integer userId) {
        List<Category> categories = repo.findByUserId(userId);

        if (categories.isEmpty()) {
            throw new NotFoundException("No categories found for this user");
        }

        GetCategoriesResponse response = new GetCategoriesResponse();

        List<NewCategoryResponse> categoryResponses ;

        categoryResponses = categories.stream().map(category -> {
            NewCategoryResponse newCategoryResponse = new NewCategoryResponse();
            newCategoryResponse.setName(category.getName());
            newCategoryResponse.setType(category.getType());
            newCategoryResponse.setCustom(category.isCustom());
            return newCategoryResponse;
        }).toList();

        response.setCategories(categoryResponses);

        return response;
    }

    public NewCategoryResponse addCategory(Integer userId, NewCategoryRequest newCategoryRequest) {
        boolean exists = repo.existsByNameAndUserId(newCategoryRequest.getName(), userId);
        if (exists) {
            throw new ConflictException("Category already exists for this user");
        }

        Category newCategory = new Category();
        newCategory.setUserId(userId);
        newCategory.setName(newCategoryRequest.getName());
        newCategory.setType(newCategoryRequest.getType());
        newCategory.setCustom(true);

        repo.save(newCategory);

        NewCategoryResponse response = new NewCategoryResponse();

        response.setName(newCategory.getName());
        response.setType(newCategory.getType());
        response.setCustom(newCategory.isCustom());

        return response;
    }

    public DeleteCategoryResponse deleteCategory(Integer userId, String categoryName) {
        Optional<Category> categoryOpt = repo.findByUserIdAndName(userId, categoryName);

        if (categoryOpt.isEmpty()) {
            throw new NotFoundException("Category not found");
        }

        Category category = categoryOpt.get();

        if (!category.isCustom()) {
            throw new ForbiddenException("Cannot delete default system categories");
        }

        List<Transaction> transactions = transactionRepo.findByUserIdAndCategory(userId, categoryName);
        if (!transactions.isEmpty()) {
            throw new ConflictException("Cannot delete category: it is referenced by existing transactions");
        }

        repo.delete(category);

        DeleteCategoryResponse response = new DeleteCategoryResponse();
        response.setMessage("Category deleted successfully");
        return response;
    }
}
