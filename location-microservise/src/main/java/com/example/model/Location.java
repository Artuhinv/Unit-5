package com.example.model;

import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Location {
    @Id @GeneratedValue
    private int id;

    @NonNull private String name;
    @NonNull private double lon;
    @NonNull private double lat;
}
