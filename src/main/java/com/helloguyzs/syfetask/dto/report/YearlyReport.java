package com.helloguyzs.syfetask.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearlyReport {

    private int year;
    private Map<String, Double> totalIncome;
    private Map<String, Double> totalExpenses;
    private double netSavings;
}
