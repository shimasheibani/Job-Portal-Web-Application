package com.dreamjob.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "job_seeker_apply", uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"user_id", "id"}
        )
})
public class JobSeekerApply implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "apply_date")
    @DateTimeFormat(pattern = "dd=MM-yyyy")
    private Date applyDate;

    @Column(name="cover_letter")
    private String coverLetter;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="job", referencedColumnName = "id")
    private JobPostActivity jobPostActivity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id" , referencedColumnName = "id")
    private JobSeekerProfile jobSeekerProfile;
}
