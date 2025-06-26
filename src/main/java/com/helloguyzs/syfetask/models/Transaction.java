package com.helloguyzs.syfetask.models;

import jakarta.persistence.*;
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

//    @Column( nullable = false)
    private Integer userId;

    @Column( nullable = false)
    private Integer amount ;

    @Column( nullable = false)
    private LocalDate date;

    @Column( nullable = false)
    private String category;


    private String description;

}


