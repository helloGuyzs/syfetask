package com.helloguyzs.syfetask.dto.goals;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGoalRequest {

    @NotBlank(message = "Goal name is required")
    private String goalName;

    @Min(value = 1, message = "Target amount should be more than zero")
    private Double  targetAmount;

    @Future(message = "Target date must be in the future")
    private LocalDate targetDate;

    private LocalDate startDate;

}
