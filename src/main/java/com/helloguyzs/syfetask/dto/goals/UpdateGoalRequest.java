package com.helloguyzs.syfetask.dto.goals;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateGoalRequest {

    @Min(value=1 , message = "Target amount must be greater than 0")
    private Double targetAmount;

    @Future(message = "Target date must be a future date")
    private LocalDate targetDate;
}
