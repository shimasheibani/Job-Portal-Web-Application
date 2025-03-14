package com.dreamjob.repository;

import com.dreamjob.entity.JobPostActivity;
import com.dreamjob.entity.JobSeekerApply;
import com.dreamjob.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerApplyRepository extends JpaRepository<JobSeekerApply, Long> {
    List<JobSeekerApply> findById(JobSeekerProfile userId);
    List<JobSeekerApply> findByJob(JobPostActivity job);
}
