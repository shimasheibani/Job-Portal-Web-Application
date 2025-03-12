package com.jobs.portal.jobportal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "skills")
public class Skills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "VARCHAR2(255)", name = "experience_level")
    private String experienceLevel;
    @Column(columnDefinition = "VARCHAR2(255)")
    private String name;
    @Column(columnDefinition = "VARCHAR2(255)", name = "year_of_experience")
    private String yearOfExperience;
    @ManyToOne()
    @JoinColumn(name="job+seeker_profile")
    private JobSeekerProfile jobSeekerProfile;
}
