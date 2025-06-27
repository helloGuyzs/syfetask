package com.helloguyzs.syfetask.controller;


import com.helloguyzs.syfetask.dto.goals.CreateGoalRequest;
import com.helloguyzs.syfetask.dto.goals.CreateGoalResponse;
import com.helloguyzs.syfetask.dto.goals.UpdateGoalRequest;
import com.helloguyzs.syfetask.dto.goals.DeleteGoalResponse;
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

        return goalsService.createGoal(request);
    }

    @GetMapping("/goals")
    public List<CreateGoalResponse> getGoalsByUserId() {

        return goalsService.getGoalsByUserId();
    }


    @GetMapping("/goals/{id}")
    public CreateGoalResponse getGoalById(@PathVariable Integer id) {
        return goalsService.getGoalById(id);
    }

    @PutMapping("/goals/{id}")
    public CreateGoalResponse updateGoal(@PathVariable Integer id, @RequestBody @Valid UpdateGoalRequest request) {
        return goalsService.updateGoal(id, request);
    }

    @DeleteMapping("/goals/{id}")
    public DeleteGoalResponse deleteGoal(@PathVariable Integer id) {
        return goalsService.deleteGoal(id);
    }


}

