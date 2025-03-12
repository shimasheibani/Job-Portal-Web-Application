package com.dreamjob.repository;

import com.dreamjob.entity.IRecruiterJobs;
import com.dreamjob.entity.JobPostActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobPostActivityRepository extends JpaRepository<JobPostActivity, Long> {
    @Query(value = "SELECT COUNT(s.user_id) AS totalCandidates, " +
            "j.id, j.job_title, " +
            "l.id AS locationId, l.city, l.state, l.country, " +
            "c.id AS companyId, c.name " +
            "FROM job_post_ctivity j " +
            "INNER JOIN job_location l ON j.job_location_id = l.id " +
            "INNER JOIN job_company c ON j.job_company_id = c.id " +
            "LEFT JOIN job_seeker_apply s ON s.job = j.id " +
            "WHERE j.posted_by = :recruiter " +
            "GROUP BY j.id, j.job_title, " +
            "l.id, l.city, l.state, l.country, " +
            "c.id, c.name",
            nativeQuery = true)


    List<IRecruiterJobs> getREcruiterJobs(@Param("recruiter") Long recruiter);
}

