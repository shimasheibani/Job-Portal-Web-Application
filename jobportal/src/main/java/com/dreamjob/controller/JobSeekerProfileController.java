package com.dreamjob.controller;

import com.dreamjob.entity.JobSeekerProfile;
import com.dreamjob.entity.Skills;
import com.dreamjob.entity.User;
import com.dreamjob.repository.UserRepository;
import com.dreamjob.service.JobSeekerProfileService;
import com.dreamjob.util.FIleUploadUtil;
import com.dreamjob.util.FileDownloadUtil;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.util.StringUtil;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/job-seeker-profile")

public class JobSeekerProfileController {
    private final JobSeekerProfileService jobSeekerProfileService;
    private final UserRepository userRepository  ;
    @GetMapping("/")
    public String jobSeekerProfile(Model model){
        JobSeekerProfile jobSeekerProfile = new JobSeekerProfile();
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        List<Skills> skills = new ArrayList<>();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
           User user =  userRepository.findByEmail(authentication.getName()).orElseThrow(()->new RuntimeException("User Not Found"));
           Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(user.getId());
           if(seekerProfile.isPresent()){
               jobSeekerProfile = seekerProfile.get();
               if(jobSeekerProfile.getSkills().isEmpty()){
                   skills.add(new Skills());
                   jobSeekerProfile.setSkills(skills);
               }

           }
            model.addAttribute("skills", skills);
            model.addAttribute("profile", jobSeekerProfile);
        }
        return "job-seeker-profile";
    }

    @PostMapping("/addNew")
    public String addNew(JobSeekerProfile jobSeekerProfile, @RequestParam("image") MultipartFile image  ,@RequestParam("pdf") MultipartFile pdf, Model model){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
            User user = userRepository.findByEmail(username).orElseThrow(()->new RuntimeException("User Not Found"));
            jobSeekerProfile.setUser(user);
            jobSeekerProfile.setId(user.getId());
        }
        List<Skills> skillsList = new ArrayList<>();
        model.addAttribute("skills", skillsList);
        model.addAttribute("profile", jobSeekerProfile);
        for(Skills skills : jobSeekerProfile.getSkills()){
            skills.setJobSeekerProfile(jobSeekerProfile);
        }
        String imageName="";
        String resuemName="";
        if(!Objects.equals(image.getOriginalFilename(),"")){
            imageName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            jobSeekerProfile.setProfilePhoto(imageName);
        }
        if(!Objects.equals(pdf.getOriginalFilename(),"")){
            resuemName = StringUtils.cleanPath(Objects.requireNonNull(pdf.getOriginalFilename()));
            jobSeekerProfile.setProfilePhoto(resuemName);
        }
        JobSeekerProfile seekerProfile = jobSeekerProfileService.addNew(jobSeekerProfile);
        try {
            String uploadDir = "/photos/candidates" + jobSeekerProfile.getUser();
            if (!Objects.equals(image.getOriginalFilename(),"")){
                FIleUploadUtil.saveFile(uploadDir, imageName, image);
            }
            if (!Objects.equals(pdf.getOriginalFilename(),"")){
                FIleUploadUtil.saveFile(uploadDir, resuemName, pdf);
            }

        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return "redirect:/job-seeker-profile/";
    }

    @GetMapping("/{id}")
    public String candidateProfile(@PathVariable("id") Long id, Model model){
        Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(id);
        model.addAttribute("profile", seekerProfile.get());
        return "job-seeker-profile";
    }

    @GetMapping("downloadResume")
    public ResponseEntity<?> downloadResume(@RequestParam(value = "fileName") String fileName, @RequestParam(value = "userId") String userId){
        FileDownloadUtil fileDownloadUtil = new FileDownloadUtil();
        Resource resource = null;
        try {
            resource = fileDownloadUtil.getFIleAsResource("photos/candidate/" + userId, fileName);
        }catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
        if(resource == null){
            return new ResponseEntity<>("File not Found", HttpStatus.NOT_FOUND);
        }
        String contentType = "appliacation/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return  ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, headerValue).body(resource);
    }
}
