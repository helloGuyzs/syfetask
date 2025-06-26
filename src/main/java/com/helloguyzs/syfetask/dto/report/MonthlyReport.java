package com.helloguyzs.syfetask.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyReport {

    private int year;
    private int month;
    private Map<String, Double> totalIncome;
    private Map<String, Double> totalExpenses;
    private double netSavings;

}
