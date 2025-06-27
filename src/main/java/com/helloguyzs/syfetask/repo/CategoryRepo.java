package com.helloguyzs.syfetask.repo;

import com.helloguyzs.syfetask.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends JpaRepository <Category, String > {

    List<Category> findByUserId(Integer userId);
    Optional<Category> findByUserIdAndName(Integer userId, String name);
    Boolean existsByNameAndUserId(String name, Integer userId);


}
