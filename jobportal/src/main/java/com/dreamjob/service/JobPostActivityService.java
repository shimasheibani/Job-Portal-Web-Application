package com.dreamjob.service;

import com.dreamjob.entity.*;
import com.dreamjob.repository.JobPostActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class JobPostActivityService {
    private final JobPostActivityRepository jobPostActivityRepository;
    public JobPostActivity addNew(JobPostActivity jobPostActivity) {
        return jobPostActivityRepository.save(jobPostActivity);
    }
    public List<RecruiterJobsDto> getRecruiterJobs(Long id) {
        List<IRecruiterJobs> recruiterJobsDto = jobPostActivityRepository.getREcruiterJobs(id);
        List<RecruiterJobsDto> recruiterJobsDtoList = new ArrayList<>();
        for(IRecruiterJobs recruiterJobs : recruiterJobsDto) {
            JobLocation location = new JobLocation(recruiterJobs.getLocationId(), recruiterJobs.getCity(),recruiterJobs.getState(),recruiterJobs.getCountry());
            JobCompany company = new JobCompany(recruiterJobs.getCompanyId(), recruiterJobs.getName(),"");
            recruiterJobsDtoList.add(new RecruiterJobsDto(recruiterJobs.getTotalCandidates(),recruiterJobs.getJobs_post_id(),recruiterJobs.getJob_title(),location,company));
        }
        return recruiterJobsDtoList;
    }
    public JobPostActivity getOne(Long id) {
        return jobPostActivityRepository.findById(id).orElseThrow(()->new RuntimeException("Job not found"));
    }
}
