package com.dreamjob.controller;

import com.dreamjob.entity.User;
import com.dreamjob.entity.UserType;
import com.dreamjob.service.UserService;
import com.dreamjob.service.UserTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserTypeService userTypeService;
    private final UserService userService;

    @GetMapping("/register")
    public String register(Model model){
        List<UserType> userTypes = userTypeService.getAll();
        model.addAttribute("getAllTypes", userTypes);
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register/new")
    public String userRegistertion(@Valid User user){
        userService.AddUser(user);
        return "redirect:/dashboard/";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return "redirect:/";
    }
}
