package com.dreamjob.controller;

import com.dreamjob.entity.RecruiterProfile;
import com.dreamjob.entity.User;
import com.dreamjob.repository.RecruiterProfileRepository;
import com.dreamjob.repository.UserRepository;
import com.dreamjob.service.RecruiterProfileService;
import com.dreamjob.util.FIleUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/recruiter-profile")
@RequiredArgsConstructor
public class RecruiterProfileController {
    private final UserRepository userRepository;
    private final RecruiterProfileService recruiterProfileService;
    @GetMapping("/")
    public String recruiterProfile(Model model) {
        System.out.println("hii");
        Authentication authenticarion = SecurityContextHolder.getContext().getAuthentication();
        if(!(authenticarion instanceof AnonymousAuthenticationToken)) {
            String curentUsername = authenticarion.getName();
            User user= userRepository.findByEmail(curentUsername).orElseThrow(()->new RuntimeException("User is not find"));
            Optional<RecruiterProfile> recruiterProfile  = recruiterProfileService.findByUser(user);
            if(!recruiterProfile.isEmpty()) {
                model.addAttribute("profile", recruiterProfile.get());
            }
        }
        return "recruiter_profile";
    }
    @PostMapping("/addNew")
    public String addNew(RecruiterProfile recruiterProfile, @RequestParam("image") MultipartFile multipartFile, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUser = authentication.getName();
            User user = userRepository.findByEmail(currentUser).orElseThrow(()->new UsernameNotFoundException("user not found"));
            recruiterProfile.setUser(user);
            recruiterProfile.setId(user.getId());

        }
        model.addAttribute("profile", recruiterProfile);
        String fileName="";
        if(!multipartFile.getOriginalFilename().equals("")){
            fileName= StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            recruiterProfile.setProfilePhoto(fileName);
        }
        RecruiterProfile savedUser= recruiterProfileService.addNew(recruiterProfile);
        String upliadDir = "photos/recruiter/" + savedUser.getId();
        try {
            FIleUploadUtil.saveFile(upliadDir,fileName,multipartFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/dashboard/";
    }
}
