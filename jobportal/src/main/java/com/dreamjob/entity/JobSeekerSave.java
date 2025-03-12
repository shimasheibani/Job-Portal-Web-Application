package com.dreamjob.entity;

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
@Table(name = "job_seeker_save")
public class JobSeekerSave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name="job")
    private JobPostActivity jobPostActivity;
    @ManyToOne()
    @JoinColumn(name="user_id")
    private JobSeekerProfile jobSeekerProfile;
}
