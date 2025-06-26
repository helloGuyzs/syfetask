package com.helloguyzs.syfetask.dto.transaction;


import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTransactionRequest {

    private Integer amount;
    private String category;
    private String description;

}
