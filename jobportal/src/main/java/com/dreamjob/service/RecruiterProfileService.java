package com.dreamjob.service;

import com.dreamjob.entity.RecruiterProfile;
import com.dreamjob.entity.User;
import com.dreamjob.repository.RecruiterProfileRepository;
import com.dreamjob.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecruiterProfileService {
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final UserRepository userRepository;

    public Optional<RecruiterProfile> getOne(Long id) {
        return recruiterProfileRepository.findById(id);
    }

    public RecruiterProfile addNew(RecruiterProfile recruiterProfile) {
        return  recruiterProfileRepository.save(recruiterProfile);
    }

    public Optional<RecruiterProfile> findByUser(User user) {
        return recruiterProfileRepository.findByUser(user);
    }

    public RecruiterProfile getCurrentRecruiterProfile() {
       Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
       if(!(authentication instanceof AnonymousAuthenticationToken)) {
           String currentUserName = authentication.getName();
           User user = userRepository.findByEmail(currentUserName).orElseThrow(()->new RuntimeException("User Not Found"));
           Optional<RecruiterProfile> recruiterProfile = getOne(user.getId());
           return recruiterProfile.orElse(null);
       }else return null;
    }
}
