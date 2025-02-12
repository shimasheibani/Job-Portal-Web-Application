package org.springbootangular.demo.security;

import lombok.RequiredArgsConstructor;
import org.springbootangular.demo.entity.User;
import org.springbootangular.demo.exceptions.NotFoundException;
import org.springbootangular.demo.repository.UserRepository;
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
        User user = userRepository.findByEmail(username).orElseThrow(() -> new NotFoundException(" User Email Not Found..."));
        return AuthUser.builder()
                .user(user)
                .build();
    }
}
