package com.dreamjob.repository;

import com.dreamjob.entity.RecruiterProfile;
import com.dreamjob.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile, Long>{
    Optional<RecruiterProfile> findByUser(User user);
}
