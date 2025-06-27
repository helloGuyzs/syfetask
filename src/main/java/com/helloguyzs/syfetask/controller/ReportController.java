package com.helloguyzs.syfetask.controller;

import com.helloguyzs.syfetask.dto.report.MonthlyReport;
import com.helloguyzs.syfetask.dto.report.YearlyReport;
import com.helloguyzs.syfetask.services.ReportService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.helloguyzs.syfetask.utils.SessionUtil.getCurrentUserId;

@RestController
@RequestMapping("/api")
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping("/reports/monthly/{year}/{month}")
    public MonthlyReport generateMonthlyReport(@PathVariable int year, @PathVariable int month, HttpServletRequest request) {
        Integer userId = getCurrentUserId(request);
        return reportService.generateMonthlyReport(userId, year, month);
    }

    @GetMapping("/reports/yearly/{year}")
    public YearlyReport generateYearlyReport(@PathVariable int year, HttpServletRequest request) {
        Integer userId = getCurrentUserId(request);
        return reportService.generateYearlyReport(userId, year);
    }
}
