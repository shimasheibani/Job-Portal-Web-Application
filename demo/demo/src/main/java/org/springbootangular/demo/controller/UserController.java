package org.springbootangular.demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springbootangular.demo.dto.Response;
import org.springbootangular.demo.dto.UserDTO;
import org.springbootangular.demo.entity.User;
import org.springbootangular.demo.service.UserService;
import org.springbootangular.demo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
//@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/allUserd")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllUSers(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Response> updateUser(@PathVariable("id") Long id, @RequestBody  @Valid UserDTO userDTO){
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteUser(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @PostMapping("/transactions/{userId}")
    public ResponseEntity<Response> getUserTransaction(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(userService.getUserTransaction(userId));
    }
    @GetMapping("/currentUser")
    public ResponseEntity<User> getCurentLoggedUser(){
        return ResponseEntity.ok(userService.getCurentLoggedUser());
    }

}
