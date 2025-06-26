package com.helloguyzs.syfetask.services;


import com.helloguyzs.syfetask.dto.category.GetCategoriesResponse;
import com.helloguyzs.syfetask.dto.category.NewCategoryRequest;
import com.helloguyzs.syfetask.enums.CategoryType;
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
    TransactionRepo transactionRepo;

    @Autowired
    CategoryRepo repo;


    public  void createDefaultCatogaries( Integer userId) {

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

    public List<GetCategoriesResponse> getAllCategories( Integer userId) {



        List<Category> categories= repo.findByUserId(userId);

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

        Integer userId=1;

        boolean exists = repo.existsByNameAndUserId( newCategoryRequestDTO.getName() , userId);
        if (exists) {
            return "Category already exists for this user";
        }

        newCategory.setUserId(1);
        newCategory.setName(newCategoryRequestDTO.getName());
        newCategory.setType(newCategoryRequestDTO.getType());
        newCategory.setCustom(true);

        repo.save(newCategory);


        return "Category added successfully";
    }

    public String deleteCategory(String categoryName) {

        Integer userId = 1;

        Optional<Category> categoryOpt = repo.findByUserIdAndName( userId ,categoryName);

        if (categoryOpt.isEmpty()) {
            return "Category not found";
        }

        Category category = categoryOpt.get();

        List<Transaction> transaction = transactionRepo.findByUserIdAndCategory(userId , categoryName);

        if (!transaction.isEmpty()) {
            return "Can not delete category, it has associated transactions";
        }

        if (!category.isCustom()) {
            return "Cannot delete default system categories";
        }

        repo.delete(category);
        return "Category deleted successfully";

    }
}
