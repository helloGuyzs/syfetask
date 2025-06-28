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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class GoalsService {
    @Autowired
    private GoalsRepo repo;

    @Autowired
    private TransactionRepo transactionRepo;

    private BigDecimal getTotalIncome(Goal goal, String type) {

        LocalDate endDate = LocalDate.now();

        LocalDate startDate = goal.getStartDate();

        List<Transaction> transactions = transactionRepo.findByUserIdAndDateBetween(
                goal.getUserId(),
                startDate,
                endDate
        );

        return transactions.stream()
                .filter(txn -> txn.getCategoryType() == CategoryType.valueOf(type))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private CreateGoalResponse goalResponse(Goal goal) {
        CreateGoalResponse response = new CreateGoalResponse();
        response.setId(goal.getId());
        response.setGoalName(goal.getName());
        response.setStartDate(goal.getStartDate());
        response.setTargetDate(goal.getTargetDate());
        response.setTargetAmount(goal.getTargetAmount());

        BigDecimal income = getTotalIncome(goal, "INCOME");
        BigDecimal expense = getTotalIncome(goal, "EXPENSE");

        BigDecimal currentProgress = income.subtract(expense).max(BigDecimal.ZERO);
        if (currentProgress.compareTo(BigDecimal.ZERO) > 0) {
            currentProgress = currentProgress.setScale(2, RoundingMode.HALF_UP);
        } else {
            currentProgress = BigDecimal.ZERO;
        }

        BigDecimal remainingAmount = goal.getTargetAmount().subtract(currentProgress);
        if (remainingAmount.compareTo(BigDecimal.ZERO) > 0) {
            remainingAmount = remainingAmount.setScale(2, RoundingMode.HALF_UP);
        }


        // Calculate percentage
        BigDecimal progressPercentage;
        if (goal.getTargetAmount().compareTo(BigDecimal.ZERO) > 0) {
            // Calculate raw percentage
            progressPercentage = currentProgress.multiply(new BigDecimal("100"))
                    .divide(goal.getTargetAmount(), 2, RoundingMode.HALF_UP);

            if (progressPercentage.compareTo(BigDecimal.ZERO) == 0) {
                progressPercentage = new BigDecimal("0.0");
            } else {
                // Convert to string and remove trailing zeros if present
                String percentageStr = progressPercentage.toString();
                if (percentageStr.endsWith("0")) {
                    progressPercentage = new BigDecimal(percentageStr.substring(0, percentageStr.length() - 1));
                }
            }
        } else {
            progressPercentage = new BigDecimal("0.0");
        }

        response.setCurrentProgress(currentProgress);
        response.setRemainingAmount(remainingAmount);
        response.setProgressPercentage(progressPercentage);

        return response;
    }

    public CreateGoalResponse createGoal(Integer userId, CreateGoalRequest requestDTO) {
        Goal goal = new Goal();
        goal.setUserId(userId);
        goal.setName(requestDTO.getGoalName());
        goal.setTargetAmount(requestDTO.getTargetAmount().setScale(2, RoundingMode.HALF_UP));
        goal.setTargetDate(requestDTO.getTargetDate());


        if (requestDTO.getStartDate() != null) {
            goal.setStartDate(requestDTO.getStartDate());
        } else {

            goal.setStartDate(LocalDate.now());
        }

        if (goal.getTargetDate().isBefore(goal.getStartDate())) {
            throw new BadRequestException("Target date cannot be before start date");
        }

        repo.save(goal);
        return goalResponse(goal);
    }

    public GoalByUserIdResponse getGoalsByUserId(Integer userId) {
        List<Goal> goalList = repo.findByUserId(userId);

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

        if (requestDTO.getTargetAmount() != null) {
            goal.setTargetAmount(requestDTO.getTargetAmount().setScale(2, RoundingMode.HALF_UP));
        }

        if (requestDTO.getTargetDate() != null) {
            LocalDate newTargetDate = requestDTO.getTargetDate();
            if (newTargetDate.isBefore(goal.getStartDate())) {
                throw new IllegalArgumentException("Target date cannot be before start date");
            }
            goal.setTargetDate(newTargetDate);
        }

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
