package com.helloguyzs.syfetask.dto.transaction;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionResponse {



    private Integer id;
    private Integer user_id;
    private Double  amount ;
    private String date;
    private String category;
    private String description;


}
