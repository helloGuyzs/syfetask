package com.helloguyzs.syfetask.services;

import com.helloguyzs.syfetask.dto.report.MonthlyReport;
import com.helloguyzs.syfetask.dto.report.YearlyReport;
import com.helloguyzs.syfetask.enums.CategoryType;
import com.helloguyzs.syfetask.exceptions.BadRequestException;
import com.helloguyzs.syfetask.exceptions.NotFoundException;
import com.helloguyzs.syfetask.models.Transaction;
import com.helloguyzs.syfetask.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private TransactionRepo repo;

    private Map<String, Double> getTotalsByType(List<Transaction> transactions, CategoryType type) {
        Map<String, Double> result = new HashMap<>();
        for (Transaction txn : transactions) {
            if (txn.getCategoryType() == type) {
                result.put(txn.getCategory(),
                        result.getOrDefault(txn.getCategory(), 0.0) + txn.getAmount());
            }
        }
        return result;
    }

    public MonthlyReport generateMonthlyReport(Integer userId, int year, int month) {

        if (year < 1900 || year > 2100) {
            throw new BadRequestException("Year must be between 1900 and 2100");
        }

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        List<Transaction> transactions = repo.findByUserIdAndDateBetween(userId, startDate, endDate);
        if (transactions.isEmpty()) {
            throw new NotFoundException("No transactions found for this month.");
        }

        Map<String, Double> incomeMap = getTotalsByType(transactions, CategoryType.INCOME);
        Map<String, Double> expenseMap = getTotalsByType(transactions, CategoryType.EXPENSE);

        double totalIncome = incomeMap.values().stream().mapToDouble(Double::doubleValue).sum();
        double totalExpenses = expenseMap.values().stream().mapToDouble(Double::doubleValue).sum();
        double netSavings = totalIncome - totalExpenses;

        MonthlyReport report = new MonthlyReport();
        report.setYear(year);
        report.setMonth(month);
        report.setTotalIncome(incomeMap);
        report.setTotalExpenses(expenseMap);
        report.setNetSavings(netSavings);

        return report;
    }

    public YearlyReport generateYearlyReport(Integer userId, int year) {

        if (year < 1900 || year > 2100) {
            throw new BadRequestException("Year must be between 1900 and 2100");
        }
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);

        List<Transaction> transactions = repo.findByUserIdAndDateBetween(userId, startDate, endDate);
        if (transactions.isEmpty()) {
            throw new NotFoundException("No transactions found for this year.");
        }

        Map<String, Double> incomeMap = getTotalsByType(transactions, CategoryType.INCOME);
        Map<String, Double> expenseMap = getTotalsByType(transactions, CategoryType.EXPENSE);

        double totalIncome = incomeMap.values().stream().mapToDouble(Double::doubleValue).sum();
        double totalExpenses = expenseMap.values().stream().mapToDouble(Double::doubleValue).sum();
        double netSavings = totalIncome - totalExpenses;

        YearlyReport report = new YearlyReport();
        report.setYear(year);
        report.setTotalIncome(incomeMap);
        report.setTotalExpenses(expenseMap);
        report.setNetSavings(netSavings);

        return report;
    }
}
