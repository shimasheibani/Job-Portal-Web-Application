package com.dreamjob.controller;

import com.dreamjob.entity.*;
import com.dreamjob.repository.UserRepository;
import com.dreamjob.service.*;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class JobSeekerApplyController {
    private final JobPostActivityService jobPostActivityService;
    private final UserService  userService;
    private final JobSeekerApplyService jobSeekerApplyService;
    private final JobSeekerSaveService jobSeekerSaveService;
    private final RecruiterProfileService recruiterProfileService;
    private final JobSeekerProfileService jobSeekerProfileService;
    private final UserRepository userRepository;

    @GetMapping("job-details-apply/{id}")
    public String display(@PathVariable("id") Long id, Model model){
        JobPostActivity jobDetails = jobPostActivityService.getOne(id);
        List<JobSeekerApply> jobSeekerApplyList = jobSeekerApplyService.getJobCandidate(jobDetails);
        List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveService.getJobCandidate(jobDetails);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))){
                RecruiterProfile user = recruiterProfileService.getCurrentRecruiterProfile();
                if(user != null){
                    model.addAttribute("applyList", jobSeekerApplyList);
                }
            }else {
                JobSeekerProfile user = jobSeekerProfileService.getCurrentSeekerProfile();
                if(user != null){
                    boolean exists = false;
                    boolean saved = false;
                    for(JobSeekerApply jobSeekerApply : jobSeekerApplyList){
                        if(jobSeekerApply.getId().equals(user.getId())){
                            exists = true;
                            break;
                        }
                    }
                    for(JobSeekerSave jobSeekerSave : jobSeekerSaveList){
                        if (jobSeekerSave.getId().equals(user.getId())){
                            saved = true;
                            break;
                        }
                    }
                    model.addAttribute("alreadyApolied", exists);
                    model.addAttribute("alreadySaved", saved);
                }
            }
        }
        JobSeekerApply jobSeekerApply = new JobSeekerApply();
        model.addAttribute("applyJob", jobSeekerApply);
        model.addAttribute("jobDetails", jobDetails);
        model.addAttribute("user", userService.getCurrentUserProfile());
        return "job-details";
    }
    @PostMapping("job=details/apply/{id}")
    public String apply(@PathParam("id") Long id, JobSeekerApply jobSeekerApply){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof  AnonymousAuthenticationToken)){
            String username = authentication.getName();
            User user= userService.findByEmail(username);
            Optional<JobSeekerProfile> seekerProfile =  jobSeekerProfileService.getOne(user.getId());
            JobPostActivity jobPostActivity = jobPostActivityService.getOne(user.getId());
            if(seekerProfile.isPresent() && jobPostActivity != null){
                jobSeekerApply.setJobSeekerProfile(seekerProfile.get());
                jobSeekerApply.setJobPostActivity(jobPostActivity);
                jobSeekerApply.setApplyDate(new Date());
            }else{
                throw new RuntimeException("Use not found");
            }
            jobSeekerApplyService.addNew(jobSeekerApply);
        }
        return "redirect:/dashboard/";
    }


}
