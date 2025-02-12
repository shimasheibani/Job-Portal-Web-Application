package com.shima.project.entity;
import lombok.*;
import javax.persistence.*;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity(name = "person")
@Table(name = "person")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "NUMBER")
    private int id;
    @Column(columnDefinition = "VARCHAR2(20)")
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "PERSON_FK")
    private List<Car> carList;
}
