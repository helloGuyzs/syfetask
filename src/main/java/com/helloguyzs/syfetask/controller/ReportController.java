package com.helloguyzs.syfetask.controller;


import com.helloguyzs.syfetask.dto.report.MonthlyReport;
import com.helloguyzs.syfetask.dto.report.YearlyReport;
import com.helloguyzs.syfetask.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@RestController
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping("/reports/monthly/{year}/{month}")
    public MonthlyReport generateMonthlyReport(@PathVariable int year, @PathVariable int month) {

        return reportService.generateMonthlyReport( year, month);
    }


    @GetMapping("/reports/yearly/{year}")
    public YearlyReport generateYearlyReport(@PathVariable int year) {

        return reportService.generateYearlyReport( year);
    }

}
