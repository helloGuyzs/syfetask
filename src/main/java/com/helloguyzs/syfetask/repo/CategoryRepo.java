package com.helloguyzs.syfetask.repo;

import com.helloguyzs.syfetask.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository <Category, String > {

}
