package org.springBootAngular.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springBootAngular.dto.Response;
import org.springBootAngular.dto.UserDTO;
import org.springBootAngular.entity.User;
import org.springBootAngular.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
//@RequiredArgsConstructor
public class UserController {
    private final UserService userService;



    @GetMapping("/allUsers")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Response> getAllUSers(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateUser(@PathVariable("id") Long id, @RequestBody  @Valid UserDTO userDTO){
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Response> deleteUser(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @GetMapping("/transactions/{userId}")
    public ResponseEntity<Response> getUserTransaction(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(userService.getUserTransaction(userId));
    }
    @GetMapping("/currentUser")
    public ResponseEntity<User> getCurentLoggedUser(){
        return ResponseEntity.ok(userService.getCurentLoggedUser());
    }

}
