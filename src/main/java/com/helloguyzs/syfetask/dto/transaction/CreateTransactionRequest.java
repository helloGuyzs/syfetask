package com.helloguyzs.syfetask.dto.transaction;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.Value;

import java.time.LocalDate;


@Data
public class CreateTransactionRequest {


    @Min(value = 1 , message = "Amount should be more than Zero")
    private Integer amount;

    @NotBlank(message = "Category is required")
    private String category;

    @PastOrPresent(message = "Date cannot be in the future")
    private LocalDate date ;
    private String description;

}
