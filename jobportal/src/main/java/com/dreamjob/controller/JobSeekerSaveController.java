package com.dreamjob.controller;

import com.dreamjob.entity.JobPostActivity;
import com.dreamjob.entity.JobSeekerProfile;
import com.dreamjob.entity.JobSeekerSave;
import com.dreamjob.entity.User;
import com.dreamjob.service.JobPostActivityService;
import com.dreamjob.service.JobSeekerProfileService;
import com.dreamjob.service.JobSeekerSaveService;
import com.dreamjob.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class JobSeekerSaveController {
    private final UserService userService;
    private final JobSeekerProfileService jobSeekerProfileService;
    private final JobPostActivityService  jobPostActivityService;
    private  final JobSeekerSaveService jobSeekerSaveService;

    @PostMapping("job-details/save/{id}")
    public String save(@PathVariable("id") Long id, JobSeekerSave jobSeekerSave){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
            User user = userService.findByEmail(username);
            Optional<JobSeekerProfile>  seekerProfile = jobSeekerProfileService.getOne(user.getId());
            JobPostActivity jobPostActivity = jobPostActivityService.getOne(user.getId());
            if(seekerProfile.isPresent() && jobPostActivity != null){
                jobSeekerSave.setJobSeekerProfile(seekerProfile.get());
                jobSeekerSave.setJobPostActivity(jobPostActivity);
            }else{
                throw new RuntimeException("User not found");
            }
            jobSeekerSaveService.addNew(jobSeekerSave);
        }
        return "redirect:/dashboard/";
    }

    @GetMapping("saved-jobs")
    public String saveJobs(Model model){
        List<JobPostActivity> jobPost = new ArrayList<>();
        Object currentUserProfile = userService.getCurrentUserProfile();

        List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveService.getCandidatesJob((JobSeekerProfile) currentUserProfile);
        for (JobSeekerSave jobSeekerSave : jobSeekerSaveList) {
            jobPost.add(jobSeekerSave.getJobPostActivity());
        }

        model.addAttribute("jobPost", jobPost);
        model.addAttribute("user", currentUserProfile);
        return "saved-jobs";
    }
}
