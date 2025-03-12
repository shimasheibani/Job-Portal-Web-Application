package com.dreamjob.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "job_company")
public class JobCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Column(columnDefinition = "VARCHAR2(255)")
    private String logo;
    @Column(columnDefinition = "VARCHAR2(255)")
    private String name;
}
