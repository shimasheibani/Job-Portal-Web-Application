package org.springBootAngular.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springBootAngular.dto.LoginRequest;
import org.springBootAngular.dto.RegisterRequest;
import org.springBootAngular.dto.Response;
import org.springBootAngular.dto.UserDTO;
import org.springBootAngular.entity.User;
import org.springBootAngular.enums.UserRole;
import org.springBootAngular.exception.InvalidCredentialsException;
import org.springBootAngular.exception.NotFoundException;
import org.springBootAngular.repository.UserRepository;
import org.springBootAngular.security.JwtUtils;
import org.springBootAngular.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtUtils jwtUtils;


    @Override
    public Response registerUser(RegisterRequest registerRequest) {
        UserRole role =UserRole.MANAGER;
        if(registerRequest.getRole() != null){
            role = registerRequest.getRole();
        }
        User userToSave = User.builder()
                .username(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .phoneNumber(registerRequest.getPhoneNumber())
                .role(role)
                .build();
        userRepository.save(userToSave);
        return Response.builder()
                .status(200)
                .message("User Create Successfully")
                .build();
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()->new NotFoundException("Email not found"));
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("passsword dies not match");
        }
        String token = jwtUtils.generateToken(user.getEmail());
        return Response.builder()
                .status(200)
                .message("Login Successfully")
                .role(user.getRole())
                .token(token)
                .expirationTime("6 months")
                .build();
    }

    @Override
    public Response getAllUser() {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
//        users.forEach(user -> user.setTransactions(null));
        List<UserDTO> userDTOs = modelMapper.map(users, new TypeToken<List<UserDTO>>() {}.getType());
        userDTOs.forEach(userDTO -> userDTO.setTransactions(null));
        return Response.builder()
                .status(200)
                .message("Success")
                .users(userDTOs)
                .build();
    }

    @Override
    public User getCurentLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new NotFoundException("User is not found"));
        user.setTransactions(null);
        return user;
    }

    @Override
    public Response updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("user does not found"));

        if(userDTO.getEmail() !=null){
            existingUser.setEmail(userDTO.getEmail());
        }
        if(userDTO.getPhoneNumber() !=null){
            existingUser.setPhoneNumber(userDTO.getPhoneNumber());
        }
        if(userDTO.getUsername() !=null){
            existingUser.setUsername(userDTO.getUsername());
        }
        if(userDTO.getRole() !=null){
            existingUser.setRole(userDTO.getRole());
        }
        if(userDTO.getPassword() != null && !userDTO.getPassword().isEmpty() ){
            existingUser.setUsername(passwordEncoder.encode(userDTO.getPassword()));
        }
        userRepository.save(existingUser);
        return Response.builder()
                .status(200)
                .message("User successfully updated")
                .build();


    }

    @Override
    public Response deleteUser(Long id) {
         userRepository.findById(id)
                 .orElseThrow(() -> new NotFoundException("User does not found"));
         userRepository.deleteById(id);
         return Response.builder()
                 .status(200)
                 .message("User Sucesssfully deleted")
                 .build();

    }

    @Override
    public Response getUserTransaction(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("User Does not found"));
        UserDTO userDTO = modelMapper.map(user , UserDTO.class);
        userDTO.getTransactions().forEach(transactonDTO -> {
            transactonDTO.setUser(null);
            transactonDTO.setSupplier(null);
        });
        return Response.builder()
                .status(200)
                .message("suuccess")
                .user(userDTO)
                .build();

    }
}
