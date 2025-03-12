package com.dreamjob.controller;

import com.dreamjob.entity.JobPostActivity;
import com.dreamjob.service.JobPostActivityService;
import com.dreamjob.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class JobSeekerApplyController {
    private final JobPostActivityService jobPostActivityService;
    private final UserService  userService;

    @GetMapping("job-details-apply/{id}")
    public String display(@PathVariable("id") Long id, Model model){
        JobPostActivity jobDetails = jobPostActivityService.getOne(id);
        model.addAttribute("jobDetails", jobDetails);
        model.addAttribute("user", userService.getCurrentUserProfile());
        return "job-details";
    }

}
