package com.helloguyzs.syfetask.controller;

import com.helloguyzs.syfetask.dto.goals.*;
import com.helloguyzs.syfetask.services.GoalsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.helloguyzs.syfetask.utils.SessionUtil.getCurrentUserId;

@RestController
@RequestMapping("/api")
public class GoalsController {

    @Autowired
    GoalsService goalsService;

    @PostMapping("/goals")
    public CreateGoalResponse createGoal(@RequestBody @Valid CreateGoalRequest request, HttpServletRequest req) {
        Integer userId = getCurrentUserId(req);
        return goalsService.createGoal(userId, request);
    }

    @GetMapping("/goals")
    public GoalByUserIdResponse getGoalsByUserId(HttpServletRequest req) {
        Integer userId = getCurrentUserId(req);
        return goalsService.getGoalsByUserId(userId);
    }

    @GetMapping("/goals/{id}")
    public CreateGoalResponse getGoalById(@PathVariable Integer id, HttpServletRequest req) {
        Integer userId = getCurrentUserId(req);
        return goalsService.getGoalById(userId, id);
    }

    @PutMapping("/goals/{id}")
    public CreateGoalResponse updateGoal(@PathVariable Integer id, @RequestBody @Valid UpdateGoalRequest request, HttpServletRequest req) {
        Integer userId = getCurrentUserId(req);
        return goalsService.updateGoal(userId, id, request);
    }

    @DeleteMapping("/goals/{id}")
    public DeleteGoalResponse deleteGoal(@PathVariable Integer id, HttpServletRequest req) {
        Integer userId = getCurrentUserId(req);
        return goalsService.deleteGoal(userId, id);
    }
}
