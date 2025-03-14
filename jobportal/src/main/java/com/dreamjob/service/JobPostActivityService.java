package com.dreamjob.service;

import com.dreamjob.entity.*;
import com.dreamjob.repository.JobPostActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public List<JobPostActivity> getAll() {
        return jobPostActivityRepository.findAll();
    }
    public List<JobPostActivity> search(String job, String location, List<String> type, List<String> remote, LocalDate searchDate) {
        return Objects.isNull(searchDate)?jobPostActivityRepository.searchWithoutDate(job,location,remote,type):
                jobPostActivityRepository.search(job,location,remote,type, searchDate);
    }


}
