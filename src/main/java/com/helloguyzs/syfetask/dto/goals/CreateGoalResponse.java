package com.helloguyzs.syfetask.dto.goals;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CreateGoalResponse {


        private Integer id;
        private String goalName;
        private BigDecimal targetAmount;
        private LocalDate startDate;
        private LocalDate targetDate;
        private BigDecimal currentProgress;
        private BigDecimal remainingAmount;
        private BigDecimal progressPercentage;


}
