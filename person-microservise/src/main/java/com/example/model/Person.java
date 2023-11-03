package com.example.model;

import lombok.*;
import jakarta.persistence.*;

@Entity @NoArgsConstructor
@AllArgsConstructor @Data
public class Person {
    @Id @GeneratedValue
    private int id;
    @NonNull private String name;
    @NonNull private String location;
}