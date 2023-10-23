package com.example.primesecur.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "t_tasks")
@Data
public class Tasks extends BaseModel{

    @Column(name = "t_date")
    private LocalDate date;

    @Column(name = "t_description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
