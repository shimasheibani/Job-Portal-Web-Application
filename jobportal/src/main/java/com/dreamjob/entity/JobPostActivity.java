package com.dreamjob.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

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
    @Column(name = "description_of_job")
    @Length(max = 10000)
    private String descriptionOfJob;
    @Column(columnDefinition = "VARCHAR2(255)", name = "job_title")
    private String jobTitle;
    @Column(columnDefinition = "VARCHAR2(255)", name = "job_type")
    private String jobType;
    @Column( name = "posted_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date postedDate;

    private String remote;
    private String salary;

    @ManyToOne()
    @JoinColumn(name="posted_by", referencedColumnName = "id")
    private User user;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="job_location_id", referencedColumnName = "id")
    private JobLocation jobLocation;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="job_company_id", referencedColumnName = "id")
    private JobCompany jobCompany;

    @Transient
    private boolean isActive;
}
