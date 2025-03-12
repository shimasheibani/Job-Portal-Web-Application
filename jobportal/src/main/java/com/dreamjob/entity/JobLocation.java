package com.jobs.portal.jobportal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "job_location")
public class JobLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR2(255)")
    private String city;
    @Column(columnDefinition = "VARCHAR2(255)")
    private String country;
    @Column(columnDefinition = "VARCHAR2(255)")
    private String state;
}
