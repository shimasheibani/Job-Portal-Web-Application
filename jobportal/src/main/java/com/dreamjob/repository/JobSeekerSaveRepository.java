package com.dreamjob.repository;

import com.dreamjob.entity.JobPostActivity;
import com.dreamjob.entity.JobSeekerProfile;
import com.dreamjob.entity.JobSeekerSave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobSeekerSaveRepository extends JpaRepository<JobSeekerSave, Long> {
    List<JobSeekerSave> findByUserId(JobSeekerProfile  id);
    List<JobSeekerSave> findByJob(JobPostActivity job);
}
