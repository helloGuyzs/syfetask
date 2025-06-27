package com.helloguyzs.syfetask.dto.goals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoalByUserIdResponse {

    private List<CreateGoalResponse> goals;
}
