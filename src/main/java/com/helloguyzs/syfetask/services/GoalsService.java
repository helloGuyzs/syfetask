package com.helloguyzs.syfetask.services;

import com.helloguyzs.syfetask.dto.goals.*;
import com.helloguyzs.syfetask.enums.CategoryType;
import com.helloguyzs.syfetask.exceptions.BadRequestException;
import com.helloguyzs.syfetask.exceptions.ForbiddenException;
import com.helloguyzs.syfetask.exceptions.NotFoundException;
import com.helloguyzs.syfetask.models.Goal;
import com.helloguyzs.syfetask.models.Transaction;
import com.helloguyzs.syfetask.repo.GoalsRepo;
import com.helloguyzs.syfetask.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class GoalsService {

    @Autowired
    GoalsRepo repo;

    @Autowired
    TransactionRepo transactionRepo;

    private Double getTotalIncome(Goal goal, String type) {
        List<Transaction> transactions = transactionRepo.findByUserIdAndDateBetween(
                goal.getUserId(),
                goal.getStartDate(),
                goal.getTargetDate()
        );

        return transactions.stream()
                .filter(txn -> txn.getCategoryType() == CategoryType.valueOf(type))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    private CreateGoalResponse goalResponse(Goal goal) {
        CreateGoalResponse response = new CreateGoalResponse();
        response.setId(goal.getId());
        response.setGoalName(goal.getName());
        response.setStartDate(goal.getStartDate());
        response.setTargetDate(goal.getTargetDate());
        response.setTargetAmount(goal.getTargetAmount());

        Double income = getTotalIncome(goal, "INCOME");
        Double expense = getTotalIncome(goal, "EXPENSE");

        double currentProgress = Math.max(income - expense, 0);
        double remainingAmount = Math.max(goal.getTargetAmount() - currentProgress, 0);
        double progressPercentage = (currentProgress / goal.getTargetAmount()) * 100;

        response.setCurrentProgress(currentProgress);
        response.setRemainingAmount(remainingAmount);
        response.setProgressPercentage(progressPercentage);

        return response;
    }

    public CreateGoalResponse createGoal(Integer userId, CreateGoalRequest requestDTO) {

        Goal goal = new Goal();
        goal.setUserId(userId);
        goal.setStartDate(LocalDate.now());
        goal.setName(requestDTO.getGoalName());
        goal.setTargetAmount(requestDTO.getTargetAmount());
        goal.setTargetDate(requestDTO.getTargetDate());

        repo.save(goal);
        return goalResponse(goal);
    }

    public GoalByUserIdResponse getGoalsByUserId(Integer userId) {
        List<Goal> goalList = repo.findByUserId(userId);

        if (goalList.isEmpty()) {
            throw new NotFoundException("No goals found for this user");
        }


        GoalByUserIdResponse response = new GoalByUserIdResponse();

        List<CreateGoalResponse> goalResponses = goalList.stream()
                .map(this::goalResponse)
                .toList();

        response.setGoals(goalResponses);
        return response;
    }

    public CreateGoalResponse getGoalById(Integer userId, Integer id) {
        Goal goal = repo.findById(id).orElseThrow(() ->
                new NotFoundException("Goal not found"));

        if (!goal.getUserId().equals(userId)) {
            throw new ForbiddenException("Access denied for this goal");
        }

        return goalResponse(goal);
    }

    public CreateGoalResponse updateGoal(Integer userId, Integer id, UpdateGoalRequest requestDTO) {
        Goal goal = repo.findById(id).orElseThrow(() ->
                new NotFoundException("Goal not found"));

        if (!goal.getUserId().equals(userId)) {
            throw new ForbiddenException("Access denied for this goal");
        }
//        if (requestDTO.getTargetAmount() != null && requestDTO.getTargetAmount() <= 0) {
//            throw new BadRequestException("Target amount must be positive");
//        }
//
//        if (requestDTO.getTargetDate() != null && !requestDTO.getTargetDate().isAfter(LocalDate.now())) {
//            throw new BadRequestException("Target date must be a future date");
//        }

        if (requestDTO.getTargetAmount() != null)
            goal.setTargetAmount(requestDTO.getTargetAmount());

        if (requestDTO.getTargetDate() != null)
            goal.setTargetDate(requestDTO.getTargetDate());

        repo.save(goal);
        return goalResponse(goal);
    }

    public DeleteGoalResponse deleteGoal(Integer userId, Integer id) {
        Goal goal = repo.findById(id).orElseThrow(() ->
                new NotFoundException("Goal not found"));

        if (!goal.getUserId().equals(userId)) {
            throw new ForbiddenException("Access denied for this goal");
        }

        repo.delete(goal);

        DeleteGoalResponse response = new DeleteGoalResponse();
        response.setMessage("Goal deleted successfully");
        return response;
    }
}
