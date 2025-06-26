package com.helloguyzs.syfetask.repo;

import com.helloguyzs.syfetask.models.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalsRepo extends JpaRepository<Goal, Integer > {


    List<Goal> findByUserId(Integer userId);
}
