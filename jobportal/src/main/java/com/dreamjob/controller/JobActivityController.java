package com.dreamjob.controller;

import com.dreamjob.entity.*;
import com.dreamjob.service.JobPostActivityService;
import com.dreamjob.service.JobSeekerApplyService;
import com.dreamjob.service.JobSeekerSaveService;
import com.dreamjob.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
//@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class JobActivityController {
    private final UserService userService;
    private final JobPostActivityService jobPostActivityService;
    private final JobSeekerApplyService jobSeekerApplyService;
    private final JobSeekerSaveService jobSeekerSaveService;

    @GetMapping("/dashboard/")
    public String searchForJob(Model model, @RequestParam(value = "job", required = false) String job,
                               @RequestParam(value = "location", required = false) String location,
                               @RequestParam(value = "partTime", required = false) String partTime,
                               @RequestParam(value = "fullTime", required = false) String fullTime,
                               @RequestParam(value = "freelancer", required = false) String freelancer,
                               @RequestParam(value = "remoteOnly", required = false) String remoteOnly,
                               @RequestParam(value = "officeOnly", required = false) String officeOnly,
                               @RequestParam(value = "partialRemote", required = false) String partialRemote,
                               @RequestParam(value = "today", required = false) boolean today,
                               @RequestParam(value = "day7", required = false) boolean day7,
                               @RequestParam(value = "day30", required = false) boolean day30
                               ){
        model.addAttribute("partTime", Objects.equals(partTime, "partTime"));
        model.addAttribute("fullTime", Objects.equals(fullTime, "fullTime"));

        model.addAttribute("freelancer", Objects.equals(freelancer, "freelancer"));
        model.addAttribute("remoteOnly", Objects.equals(remoteOnly, "remoteOnly"));
        model.addAttribute("officeOnly", Objects.equals(officeOnly, "officeOnly"));
        model.addAttribute("partialRemote", Objects.equals(partialRemote, "partialRemote"));

        model.addAttribute("today", today);
        model.addAttribute("day7", day7);
        model.addAttribute("day30", day30);

        model.addAttribute("job", job);
        model.addAttribute("location", location);

        LocalDate searchDate = null;
        List<JobPostActivity> jobPost = null;
        boolean dataSearchFlag = true;
        boolean remote = true;
        boolean types = true;
        if(day30){
            searchDate= LocalDate.now().minusDays(30);
        }else if(day7){
            searchDate= LocalDate.now().minusDays(7);
        }else if(today){
            searchDate= LocalDate.now();
        }else {
            dataSearchFlag= false;
        }

        if(partTime==null && fullTime == null && freelancer==null ){
            partTime="Part-Time";
            fullTime="Full-Time";
            freelancer="Freelancer";
            remote =false;
        }
        if(officeOnly == null && remoteOnly == null && partialRemote==null ){
            officeOnly="Office-Only";
            remoteOnly="Remote-Only";
            partialRemote="Partial-Remote";
            types= false;
        }

        if(!dataSearchFlag && !remote && !types && !StringUtils.hasText((job))&& !StringUtils.hasText(location)){
            jobPost = jobPostActivityService.getAll();
        }else{
            jobPost = jobPostActivityService.search(job,location, Arrays.asList(partTime,fullTime,freelancer),
                    Arrays.asList(remoteOnly,officeOnly,partialRemote), searchDate);
        }


        Object currentUserProfile = userService.getCurrentUserProfile();
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String curentUserName= authentication.getName();
            model.addAttribute("username", curentUserName);
            if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))){
                List<RecruiterJobsDto> recruiterJobs = jobPostActivityService.getRecruiterJobs(((RecruiterProfile)
                currentUserProfile).getId());
                model.addAttribute("jobPosts", recruiterJobs);
            }else{
                List<JobSeekerApply> jobSeekerApplyList = jobSeekerApplyService.getCandidaterJobs((JobSeekerProfile) currentUserProfile );
                List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveService.getCandidatesJob((JobSeekerProfile) currentUserProfile);
                boolean exist;
                boolean saved;
                for(JobPostActivity jobActivity : jobPost){
                    exist=false;
                    saved=false;
                    for (JobSeekerApply jobSeekerApply : jobSeekerApplyList) {
                        if(Objects.equals(jobActivity.getId(), jobSeekerApply.getJobPostActivity().getId())){
                            jobActivity.setActive(true);
                            exist=true;
                            break;
                        }
                    }
                    for(JobSeekerSave jobSeekerSave : jobSeekerSaveList){
                        if(Objects.equals(jobActivity.getId(), jobSeekerSave.getJobPostActivity().getId())){
                            jobActivity.setSaved(true);
                            saved=true;
                            break;
                        }
                    }
                    if (!exist){
                        jobActivity.setActive(false);
                    }
                    if (!saved){
                        jobActivity.setSaved(false);
                    }
                    model.addAttribute("jobPost", jobPost);
                }
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

    @GetMapping("global-search/")
    public String globalSearch(Model model, @RequestParam(value = "job", required = false) String job,
                               @RequestParam(value = "location", required = false) String location,
                               @RequestParam(value = "partTime", required = false) String partTime,
                               @RequestParam(value = "fullTime", required = false) String fullTime,
                               @RequestParam(value = "freelancer", required = false) String freelancer,
                               @RequestParam(value = "remoteOnly", required = false) String remoteOnly,
                               @RequestParam(value = "officeOnly", required = false) String officeOnly,
                               @RequestParam(value = "partialRemote", required = false) String partialRemote,
                               @RequestParam(value = "today", required = false) boolean today,
                               @RequestParam(value = "day7", required = false) boolean day7,
                               @RequestParam(value = "day30", required = false) boolean day30){

        model.addAttribute("partTime", Objects.equals(partTime, "partTime"));
        model.addAttribute("fullTime", Objects.equals(fullTime, "fullTime"));

        model.addAttribute("freelancer", Objects.equals(freelancer, "freelancer"));
        model.addAttribute("remoteOnly", Objects.equals(remoteOnly, "remoteOnly"));
        model.addAttribute("officeOnly", Objects.equals(officeOnly, "officeOnly"));
        model.addAttribute("partialRemote", Objects.equals(partialRemote, "partialRemote"));

        model.addAttribute("today", today);
        model.addAttribute("day7", day7);
        model.addAttribute("day30", day30);

        model.addAttribute("job", job);
        model.addAttribute("location", location);

        LocalDate searchDate = null;
        List<JobPostActivity> jobPost = null;
        boolean dataSearchFlag = true;
        boolean remote = true;
        boolean types = true;
        if(day30){
            searchDate= LocalDate.now().minusDays(30);
        }else if(day7){
            searchDate= LocalDate.now().minusDays(7);
        }else if(today){
            searchDate= LocalDate.now();
        }else {
            dataSearchFlag= false;
        }

        if(partTime==null && fullTime == null && freelancer==null ){
            partTime="Part-Time";
            fullTime="Full-Time";
            freelancer="Freelancer";
            remote =false;
        }
        if(officeOnly == null && remoteOnly == null && partialRemote==null ){
            officeOnly="Office-Only";
            remoteOnly="Remote-Only";
            partialRemote="Partial-Remote";
            types= false;
        }

        if(!dataSearchFlag && !remote && !types && !StringUtils.hasText((job))&& !StringUtils.hasText(location)){
            jobPost = jobPostActivityService.getAll();
        }else{
            jobPost = jobPostActivityService.search(job,location, Arrays.asList(partTime,fullTime,freelancer),
                    Arrays.asList(remoteOnly,officeOnly,partialRemote), searchDate);
        }

        model.addAttribute("jobPost", jobPost);
        return "global-search";

    }

}
