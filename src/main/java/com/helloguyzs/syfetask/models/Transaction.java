package com.helloguyzs.syfetask.models;

import com.helloguyzs.syfetask.enums.CategoryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Not;

import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column( nullable = false)
    private Integer userId;

    @Column(nullable = false)
    @Min(1)
    private Double  amount;

    @Column( nullable = false)
//    @PastOrPresent(message = "Date cannot be in the future")
    private LocalDate date;

    @Column( nullable = false)
    private String category;

    @Column( nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;


    private String description;

}


