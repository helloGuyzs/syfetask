package com.helloguyzs.syfetask.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goal {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Min(1)
    private BigDecimal targetAmount;

    @Column(nullable = false)
    private LocalDate startDate;

    @Future(message = "Target date must be a future date")
    @Column(nullable = false)
    private LocalDate targetDate;
}
