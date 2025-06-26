package com.helloguyzs.syfetask.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @Column(unique = true, nullable = false)
    private String username;

    @Column( nullable = false)
    private  String fullName ;

    @Column( nullable = false)
    private String password ;

    @Column( nullable = false)
    private String phone ;

}
