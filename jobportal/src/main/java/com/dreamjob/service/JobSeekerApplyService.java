package com.dreamjob.service;

import com.dreamjob.entity.JobPostActivity;
import com.dreamjob.entity.JobSeekerApply;
import com.dreamjob.entity.JobSeekerProfile;
import com.dreamjob.repository.JobSeekerApplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobSeekerApplyService {
    private final JobSeekerApplyRepository jobSeekerApplyRepository;
    public List<JobSeekerApply> getCandidaterJobs(JobSeekerProfile userId) {
        return  jobSeekerApplyRepository.findById(userId);
    }
    public List<JobSeekerApply> getJobCandidate(JobPostActivity job) {
        return  jobSeekerApplyRepository.findByJob(job);
    }

    public void addNew(JobSeekerApply jobSeekerApply) {
        jobSeekerApplyRepository.save(jobSeekerApply);
    }
}
