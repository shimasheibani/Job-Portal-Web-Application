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
@Table(name = "job_seeker_apply")
public class JobSeekerApply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "VARCHAR2(255)", name = "apply_date")
    private LocalDateTime applyDate;
    @Column(columnDefinition = "VARCHAR2(255)", name="cover_letter")
    private String coverLetter;
    @ManyToOne()
    @JoinColumn(name="job")
    private JobPostActivity jobPostActivity;
    @ManyToOne()
    @JoinColumn(name="user_id")
    private JobSeekerProfile jobSeekerProfile;
}
