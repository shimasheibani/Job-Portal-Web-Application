package com.dreamjob.service;

import com.dreamjob.entity.JobSeekerProfile;
import com.dreamjob.entity.User;
import com.dreamjob.repository.JobSeekerProfileRepository;
import com.dreamjob.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobSeekerProfileService {
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final UserRepository userRepository;
    public Optional<JobSeekerProfile> getOne(Long id) {
        return jobSeekerProfileRepository.findById(id);
    }

    public JobSeekerProfile addNew(JobSeekerProfile jobSeekerProfile) {
        return  jobSeekerProfileRepository.save(jobSeekerProfile);
    }

    public JobSeekerProfile getCurrentSeekerProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if((!(authentication instanceof AnonymousAuthenticationToken))){
            String username = authentication.getName();
            User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
            Optional<JobSeekerProfile> jobSeekerProfile = jobSeekerProfileRepository.findById(user.getId());
            return jobSeekerProfile.orElse(null);
        }
        else return null;
    }
}
