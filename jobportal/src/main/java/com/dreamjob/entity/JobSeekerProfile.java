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
@Table(name = "job_seeker_profile")
public class JobSeekerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "VARCHAR2(255)")
    private String city;
    @Column(columnDefinition = "VARCHAR2(255)")
    private String country;
    @Column(columnDefinition = "VARCHAR2(255)", name = "employment_type")
    private String employmentType;
    @Column(columnDefinition = "VARCHAR2(255)", name = "first_name")
    private String firstName;
    @Column(columnDefinition = "VARCHAR2(255)", name = "last_name")
    private String lastName;
    @Column(columnDefinition = "VARCHAR2(64)" , name = "profile_photo")
    private String profilePhoto;
    @Column(columnDefinition = "VARCHAR2(255)")
    private String resume;
    @Column(columnDefinition = "VARCHAR2(255)")
    private String state;
    @Column(columnDefinition = "VARCHAR2(255)", name="work_authorization")
    private String workAuthorization;
}
