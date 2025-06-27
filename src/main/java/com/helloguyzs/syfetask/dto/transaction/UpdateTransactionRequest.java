package com.helloguyzs.syfetask.dto.transaction;


import jakarta.validation.constraints.Min;
import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTransactionRequest {

    @Min(value = 1 , message = "Amount must be greater than 0")
    private Double  amount;
    private String category;
    private String description;

}
