package com.example.primesecur.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@MappedSuperclass
public class BaseModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
