package com.dreamjob.controller;

import com.dreamjob.entity.JobPostActivity;
import com.dreamjob.entity.RecruiterJobsDto;
import com.dreamjob.entity.RecruiterProfile;
import com.dreamjob.entity.User;
import com.dreamjob.service.JobPostActivityService;
import com.dreamjob.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
//@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class JobActivityController {
    private final UserService userService;
    private final JobPostActivityService jobPostActivityService;

    @GetMapping("/dashboard/")
    public String searchForJob(Model model){

        Object currentUserProfile = userService.getCurrentUserProfile();
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String curentUserName= authentication.getName();
            model.addAttribute("username", curentUserName);
            if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))){
                List<RecruiterJobsDto> recruiterJobs = jobPostActivityService.getRecruiterJobs(((RecruiterProfile)
                currentUserProfile).getId());
                model.addAttribute("jobPosts", recruiterJobs);
            }
        }

        model.addAttribute("user", currentUserProfile);
//        return "redirect:/dashboard/";
        return "dashboard";
    }
    @GetMapping("/dashboard/add")
    public String addJobs(Model model){
        model.addAttribute("jobPostActivity", new JobPostActivity());
        model.addAttribute("user", userService.getCurrentUserProfile());
        return "add-jobs";
    }
    @PostMapping("/dashboard/addNew")
    public String addNew(JobPostActivity jobPostActivity, Model model){
        User user = userService.getCurrentUser();
        if(user != null){
            jobPostActivity.setUser(user);
        }
        jobPostActivity.setPostedDate(new Date());
        model.addAttribute("jobPostActivity",jobPostActivity);
        JobPostActivity saved = jobPostActivityService.addNew(jobPostActivity);
        return "dashboard";
    }
    @PostMapping("/dashboard/edit/{id}")
    public String editJob(@PathVariable("id") Long id, Model model){
        JobPostActivity jobDetails = jobPostActivityService.getOne(id);
        model.addAttribute("jobDetails", jobDetails);
        model.addAttribute("user", userService.getCurrentUserProfile());
        return "add-jobs";
    }

}
