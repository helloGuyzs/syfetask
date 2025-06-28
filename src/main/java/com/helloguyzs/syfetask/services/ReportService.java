package com.helloguyzs.syfetask.services;

import com.helloguyzs.syfetask.dto.report.MonthlyReport;
import com.helloguyzs.syfetask.dto.report.YearlyReport;
import com.helloguyzs.syfetask.enums.CategoryType;
import com.helloguyzs.syfetask.exceptions.BadRequestException;
import com.helloguyzs.syfetask.models.Transaction;
import com.helloguyzs.syfetask.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private TransactionRepo repo;

    private Map<String, BigDecimal> getTotalsByType(List<Transaction> transactions, CategoryType type) {
        Map<String, BigDecimal> result = new HashMap<>();
        for (Transaction txn : transactions) {
            if (txn.getCategoryType() == type) {
                BigDecimal currentAmount = result.getOrDefault(txn.getCategory(), BigDecimal.ZERO);
                result.put(txn.getCategory(),
                        currentAmount.add(txn.getAmount()).setScale(2, RoundingMode.HALF_UP));
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

        Map<String, BigDecimal> incomeMap = new HashMap<>();
        Map<String, BigDecimal> expenseMap = new HashMap<>();

        if (!transactions.isEmpty()) {
            incomeMap = getTotalsByType(transactions, CategoryType.INCOME);
            expenseMap = getTotalsByType(transactions, CategoryType.EXPENSE);
        }

        BigDecimal totalIncome = incomeMap.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalExpenses = expenseMap.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal netSavings = totalIncome.subtract(totalExpenses);
        // Format netSavings - if zero, don't show decimals
        if (netSavings.compareTo(BigDecimal.ZERO) == 0) {
            netSavings = BigDecimal.ZERO;
        } else {
            netSavings = netSavings.setScale(2, RoundingMode.HALF_UP);
        }


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

        Map<String, BigDecimal> incomeMap = new HashMap<>();
        Map<String, BigDecimal> expenseMap = new HashMap<>();

        if (!transactions.isEmpty()) {
            incomeMap = getTotalsByType(transactions, CategoryType.INCOME);
            expenseMap = getTotalsByType(transactions, CategoryType.EXPENSE);
        }

        BigDecimal totalIncome = incomeMap.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalExpenses = expenseMap.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);


        BigDecimal netSavings = totalIncome.subtract(totalExpenses);

        if (netSavings.compareTo(BigDecimal.ZERO) == 0) {
            netSavings = BigDecimal.ZERO;
        } else {
            netSavings = netSavings.setScale(2, RoundingMode.HALF_UP);
        }

        YearlyReport report = new YearlyReport();
        report.setYear(year);
        report.setTotalIncome(incomeMap);
        report.setTotalExpenses(expenseMap);
        report.setNetSavings(netSavings);

        return report;
    }
}
