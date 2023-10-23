package com.example.primesecur.controller;

import com.example.primesecur.model.Permission;
import com.example.primesecur.model.User;
import com.example.primesecur.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;

@Controller
public class SecController {

    @Autowired
    private UserService userService;


    @GetMapping(value = "/sign-in-page")
    public String signinPage(){
        return "signin";
    }

    @GetMapping(value = "/sign-up-page")
    public String signupPage(){
        return "signup";
    }

    @GetMapping(value = "/update-password-page")
    public String updatePasswordPage(){
        return "update-password";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/profile")
    public String profilePage(){
        return "profile";
    }

    @PostMapping(value = "/to-sign-up")
    public String toSignUp(@RequestParam(name = "user_email") String email,
                           @RequestParam(name = "user_password") String password,
                           @RequestParam(name = "user_repeat_password") String repeatPassword,
                           @RequestParam(name = "user_full_name") String fullName){
        if (password.equals(repeatPassword)){
            User user = new User();
            user.setEmail(email);
            user.setFullName(fullName);
            user.setPassword(password);
            user.setPermissions(new ArrayList<>(Collections.singleton(new Permission("ROLE_USER"))));
            User newUser = userService.addUser(user);
            if (newUser != null){
                return "redirect:/sign-up-page?success";
            }else {
                return "redirect:/sign-up-page?emailerror";
            }
        }else {
            return "redirect:/sign-up-page?passworderror";
        }
    }

    @PostMapping(value = "/to-update-password")
    public String toUpdatePassword(@RequestParam(name = "user_old_password") String oldPassword,
                                   @RequestParam(name = "user_new_password") String newPassword,
                                   @RequestParam(name = "user_repeat_new_password") String repeatNewPassword){
        if (newPassword.equals(repeatNewPassword)){
            User user = userService.updatePassword(newPassword, oldPassword);
            if (user != null){
                return "redirect:/update-password-page?success";
            }else {
                return "redirect:/update-password-page?oldpassworderror";
            }
        }else {
            return "redirect:/update-password-page?passwordmismatch";
        }
    }

}
