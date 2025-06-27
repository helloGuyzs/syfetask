package com.helloguyzs.syfetask.controller;


import com.helloguyzs.syfetask.dto.goals.*;
import com.helloguyzs.syfetask.services.GoalsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GoalsController {

    @Autowired
    GoalsService goalsService;

    @PostMapping("/goals")
    public CreateGoalResponse createGoal(@RequestBody @Valid CreateGoalRequest request) {

        Integer userId = 1;
        return goalsService.createGoal(userId, request);
    }

    @GetMapping("/goals")
    public GoalByUserIdResponse getGoalsByUserId() {

        Integer userId = 1;
        return goalsService.getGoalsByUserId(userId);
    }


    @GetMapping("/goals/{id}")
    public CreateGoalResponse getGoalById(@PathVariable Integer id) {
        Integer userId = 1;
        return goalsService.getGoalById( userId , id);
    }

    @PutMapping("/goals/{id}")
    public CreateGoalResponse updateGoal(@PathVariable Integer id, @RequestBody @Valid UpdateGoalRequest request) {
        Integer userId = 1;
        return goalsService.updateGoal( userId, id, request);
    }

    @DeleteMapping("/goals/{id}")
    public DeleteGoalResponse deleteGoal(@PathVariable Integer id) {
        Integer userId = 1;
        return goalsService.deleteGoal(userId, id);
    }


}

