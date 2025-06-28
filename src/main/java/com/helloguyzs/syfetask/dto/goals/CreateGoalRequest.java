package com.helloguyzs.syfetask.dto.goals;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGoalRequest {

    @NotBlank(message = "Goal name is required")
    private String goalName;

    @NotNull(message = "Target amount is required")
    @Min(value = 1, message = "Target amount must be greater than 0")
    private BigDecimal  targetAmount;

    @NotNull(message = "Target date is required")
    @Future(message = "Target date must be a future date")
    private LocalDate targetDate;

    private LocalDate startDate;

}
