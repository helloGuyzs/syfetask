package com.helloguyzs.syfetask.dto.goals;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CreateGoalResponse {


        private Integer id;
        private String goalName;
        private Double  targetAmount;
        private LocalDate targetDate;
        private LocalDate startDate;
        private Double  currentProgress;
        private Double progressPercentage;
        private Double  remainingAmount;


}
