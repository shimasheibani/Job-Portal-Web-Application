package com.dreamjob.service;

import com.dreamjob.entity.JobPostActivity;
import com.dreamjob.entity.JobSeekerProfile;
import com.dreamjob.entity.JobSeekerSave;
import com.dreamjob.repository.JobSeekerSaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobSeekerSaveService {
    private final JobSeekerSaveRepository jobSeekerSaveRepository;
    public List<JobSeekerSave> getCandidatesJob(JobSeekerProfile id){
        return jobSeekerSaveRepository.findByUserId(id);
    }
    public List<JobSeekerSave> getJobCandidate(JobPostActivity job){
        return jobSeekerSaveRepository.findByJob(job);
    }

    public void addNew(JobSeekerSave jobSeekerSave) {
        jobSeekerSaveRepository.save(jobSeekerSave);
    }
}
