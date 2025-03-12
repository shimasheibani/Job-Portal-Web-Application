package com.dreamjob.service;

import com.dreamjob.entity.RecruiterProfile;
import com.dreamjob.entity.User;
import com.dreamjob.repository.RecruiterProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecruiterProfileService {
    private final RecruiterProfileRepository recruiterProfileRepository;

    public Optional<RecruiterProfile> getOne(Long id) {
        return recruiterProfileRepository.findById(id);
    }

    public RecruiterProfile addNew(RecruiterProfile recruiterProfile) {
        return  recruiterProfileRepository.save(recruiterProfile);
    }

    public Optional<RecruiterProfile> findByUser(User user) {
        return recruiterProfileRepository.findByUser(user);
    }
}
