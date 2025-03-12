package com.dreamjob.service;

import com.dreamjob.entity.JobSeekerProfile;
import com.dreamjob.entity.RecruiterProfile;
import com.dreamjob.entity.User;
import com.dreamjob.repository.JobSeekerProfileRepository;
import com.dreamjob.repository.RecruiterProfileRepository;
import com.dreamjob.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private  final UserRepository userRepository;
    private  final RecruiterProfileRepository recruiterProfileRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public User AddUser(User user) {
        user.set_active(true);
        user.setRegistrationDate(new Date(System.currentTimeMillis()));
        Long userTypeId = user.getUserTypeId().getUserTypeId();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        if(userTypeId==1) {
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        }else {
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        }
        return  savedUser;
    }

    public Object getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("USer not found."));
            Long userId = user.getId();
            if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))){
                RecruiterProfile recruiterProfile = recruiterProfileRepository.findById(userId).orElseThrow(()->new UsernameNotFoundException("Recruiter not found."));
                return recruiterProfile;
            }else{
                jobSeekerProfileRepository.findById(userId).orElseThrow(()->new UsernameNotFoundException("JobSeeker not found."));
            }

        }
        return null;
    }
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String name = authentication.getName();
            User user = userRepository.findByEmail(name).orElseThrow(()->new UsernameNotFoundException("User an not find."));
            return  user;
        }
        return null;
    }
}
