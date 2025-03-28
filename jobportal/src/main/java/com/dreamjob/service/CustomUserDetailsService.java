package com.dreamjob.service;

import com.dreamjob.entity.User;
import com.dreamjob.repository.UserRepository;
import com.dreamjob.util.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException(username +" not found"));
        return new CustomUserDetails(user);
    }
}
