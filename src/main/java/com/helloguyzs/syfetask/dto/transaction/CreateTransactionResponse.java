package com.helloguyzs.syfetask.dto.transaction;


import com.helloguyzs.syfetask.enums.CategoryType;
import com.helloguyzs.syfetask.models.Category;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionResponse {



    private Integer id;
    private Integer user_id;
    private Double  amount ;
    private LocalDate date;
    private String category;
    private String description;

    @Enumerated(EnumType.STRING)
    private CategoryType type;


}
