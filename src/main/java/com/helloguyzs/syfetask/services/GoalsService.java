package com.helloguyzs.syfetask.services;


import com.helloguyzs.syfetask.dto.goals.CreateGoalRequest;
import com.helloguyzs.syfetask.dto.goals.CreateGoalResponse;
import com.helloguyzs.syfetask.dto.goals.UpdateGoalRequest;
import com.helloguyzs.syfetask.dto.goals.DeleteGoalResponse;
import com.helloguyzs.syfetask.enums.CategoryType;
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


    private Double getTotalIncome(Goal goal , String type){

         Integer userId = 1;
        List<Transaction> transactions = transactionRepo.findByUserIdAndDateBetween(
                goal.getUserId(),
                goal.getStartDate(),
                goal.getTargetDate()
        );

        System.out.println("Transactions: " + transactions);

        double income = transactions.stream()
                .filter(txn -> txn.getCategoryType() == CategoryType.valueOf(type))
                .mapToDouble(Transaction::getAmount)
                .sum();


        return income;
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

        double currentProgress = income - expense;
        currentProgress = Math.max(currentProgress, 0);
        double remainingAmount = Math.max(goal.getTargetAmount() - currentProgress, 0);
        double progressPercentage = (currentProgress / goal.getTargetAmount()) * 100;

        System.out.println("Income" + income);
        System.out.println("Expense: " + expense);
        System.out.println("Current Progress: " + currentProgress);
        System.out.println("Remaining Amount: " + remainingAmount);
        System.out.println("Progress Percentage: " + progressPercentage);

        response.setCurrentProgress(currentProgress);
        response.setRemainingAmount(remainingAmount);
        response.setProgressPercentage(progressPercentage);

        return response;
    }

    public CreateGoalResponse createGoal(CreateGoalRequest requestDTO) {

        Integer userId = 1;
        Goal goal = new Goal();


        goal.setUserId(userId);
        goal.setStartDate(LocalDate.now());

        goal.setName(requestDTO.getGoalName());
        goal.setTargetAmount(requestDTO.getTargetAmount());
        goal.setTargetDate(requestDTO.getTargetDate());

        Double income = getTotalIncome(goal, "INCOME");

        Double expense = getTotalIncome(goal, "EXPENSE");

        double currentProgress = income - expense;
        currentProgress = Math.max(currentProgress, 0);
        double remainingAmount = Math.max(goal.getTargetAmount() - currentProgress, 0);
        double progressPercentage = (currentProgress / goal.getTargetAmount()) * 100;


//
//        goal.setCurrentProgress(currentProgress);
//        goal.setProgressPercentage(progressPercentage);
//        goal.setRemainingAmount(remainingAmount);


        repo.save(goal);

        CreateGoalResponse response = goalResponse(goal);

        return response;
    }


    public List<CreateGoalResponse> getGoalsByUserId() {
        Integer userId = 1;


        List<Goal> goal = repo.findByUserId(userId);

        List<CreateGoalResponse> responseList = goal.stream().map(g -> {
            CreateGoalResponse response = goalResponse(g);
            return response;
        }).toList();


        return responseList;


    }


    public CreateGoalResponse getGoalById(Integer id) {

        Optional<Goal> goalOpt = repo.findById(id);

        if(!goalOpt.isPresent()) {
            return null;
        }

        Goal goal = goalOpt.get();

        CreateGoalResponse response = goalResponse(goal);

        return response;

    }

    public CreateGoalResponse updateGoal(Integer id , UpdateGoalRequest requestDTO) {

        Optional<Goal> goalOpt = repo.findById(id);

        if(!goalOpt.isPresent()) {
            return null;
        }

        Goal goal = goalOpt.get();

        if (requestDTO.getTargetAmount() != null) {
            goal.setTargetAmount(requestDTO.getTargetAmount());
        }

        if (requestDTO.getTargetDate() != null) {
            goal.setTargetDate(requestDTO.getTargetDate());
        }

        repo.save(goal);

        CreateGoalResponse response = goalResponse(goal);

        return response;
    }

    public DeleteGoalResponse deleteGoal(Integer id) {

        Optional<Goal> goalOpt = repo.findById(id);

        if(!goalOpt.isPresent()) {
            return null;
        }

        Goal goal = goalOpt.get();

        repo.delete(goal);

        DeleteGoalResponse response = new DeleteGoalResponse();

        response.setMessage("Goal deleted successfully");

        return response;

    }



}
