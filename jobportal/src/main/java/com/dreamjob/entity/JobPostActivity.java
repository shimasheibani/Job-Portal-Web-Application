package com.jobs.portal.jobportal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "job_post_ctivity")

public class JobPostActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "VARCHAR2(255)", name = "description_of_job")
    private String descriptionOfJob;
    @Column(columnDefinition = "VARCHAR2(255)", name = "job_title")
    private String jobTitle;
    @Column(columnDefinition = "VARCHAR2(255)", name = "job_type")
    private String jobType;
    @Column(columnDefinition = "VARCHAR2(255)", name = "posted_date")
    private LocalDateTime postedDate;
    @Column(columnDefinition = "VARCHAR2(255)", name = "remote")
    private String remote;
    @Column(columnDefinition = "VARCHAR2(255)", name = "salary")
    private String salary;

    @ManyToOne()
    @JoinColumn(name="posted_by")
    private User user;
    @ManyToOne()
    @JoinColumn(name="job_location_id")
    private JobLocation jobLocation;
    @ManyToOne()
    @JoinColumn(name="job_company_id")
    private JobCompany jobCompany;
}
