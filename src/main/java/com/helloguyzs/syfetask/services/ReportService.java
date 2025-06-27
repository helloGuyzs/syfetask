package com.helloguyzs.syfetask.services;


import com.helloguyzs.syfetask.dto.report.MonthlyReport;
import com.helloguyzs.syfetask.dto.report.YearlyReport;
import com.helloguyzs.syfetask.enums.CategoryType;
import com.helloguyzs.syfetask.models.Transaction;
import com.helloguyzs.syfetask.repo.TransactionRepo;
import com.helloguyzs.syfetask.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    TransactionRepo repo;

    @Autowired
    SecurityUtils securityUtils;

    private Map< String , Double> totalIncome( List<Transaction> transactions, String type ){

        Map<String , Double> response= new HashMap<>();

        for (Transaction transaction : transactions){
            if(transaction.getCategoryType() == CategoryType.valueOf(type)) {
                response.put(transaction.getCategory(),
                        response.getOrDefault(transaction.getCategory(),
                        0.0) + transaction.getAmount());
            }
        }

        return response;
    }

    public MonthlyReport generateMonthlyReport(int year, int month) {
        // Logic to generate monthly report
        // This is a placeholder implementation

        Integer userId = securityUtils.getCurrentUserId();

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        List<Transaction> transactions = repo.findByUserIdAndDateBetween(userId, startDate, endDate);
        Map<String, Double> incomeWithCategory = totalIncome(transactions, "INCOME");
        Map<String, Double> expenseWithCategory = totalIncome(transactions, "EXPENSE");

        double totalIncome = incomeWithCategory.values().stream().mapToDouble(Double::doubleValue).sum();
        double totalExpenses = expenseWithCategory.values().stream().mapToDouble(Double::doubleValue).sum();
        double netSavings = totalIncome - totalExpenses;

        MonthlyReport monthlyReport = new MonthlyReport();

        monthlyReport.setYear(year);
        monthlyReport.setMonth(month);
        monthlyReport.setTotalIncome(incomeWithCategory);
        monthlyReport.setTotalExpenses(expenseWithCategory);
        monthlyReport.setNetSavings(netSavings);

//        Map<String, Object> report = new HashMap<>();
//        report.put("month", month);
//        report.put("year", year);
//        report.put("totalIncome", incomeWithCategory);
//        report.put("totalExpenses", expenseWithCategory);
//        report.put("netSavings", netSavings);

        return monthlyReport;

    }


    public YearlyReport generateYearlyReport(int year) {
        Integer userId = securityUtils.getCurrentUserId();

        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);

        List<Transaction> transactions = repo.findByUserIdAndDateBetween(userId, startDate, endDate);

        Map<String, Double> incomeWithCategory = totalIncome(transactions, "INCOME");
        Map<String, Double> expenseWithCategory = totalIncome(transactions, "EXPENSE");

        double totalIncome = incomeWithCategory.values().stream().mapToDouble(Double::doubleValue).sum();
        double totalExpenses = expenseWithCategory.values().stream().mapToDouble(Double::doubleValue).sum();
        double netSavings = totalIncome - totalExpenses;


        YearlyReport yearlyReport = new YearlyReport();

        yearlyReport.setYear(year);
        yearlyReport.setTotalIncome(incomeWithCategory);
        yearlyReport.setTotalExpenses(expenseWithCategory);
        yearlyReport.setNetSavings(netSavings);

//        Map<String, Object> report = new HashMap<>();
//        report.put("year", year);
//        report.put("totalIncome", incomeByCategory);
//        report.put("totalExpenses", expenseByCategory);
//        report.put("netSavings", netSavings);

        return yearlyReport;
    }

}
