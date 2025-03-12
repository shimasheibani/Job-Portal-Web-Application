package com.jobs.portal.jobportal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name= "users_job_portal")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR2(255)")
    private String email;
    @Column(columnDefinition = "VARCHAR2(255)")
    private boolean is_active;
    @Column(columnDefinition = "VARCHAR2(255)")
    private String password;
    @Column(columnDefinition = "VARCHAR2(255)", name = "registration_date")
    private String registrationDate;
    @ManyToOne
    @JoinColumn(name="user_type")
    private UserType userType;

}
