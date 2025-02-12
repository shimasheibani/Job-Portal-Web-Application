package com.shima.project.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name="car")
@Table(name = "car")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(columnDefinition = "NUMBER")
    private String model;
}
