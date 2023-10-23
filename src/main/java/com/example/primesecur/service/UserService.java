package com.example.primesecur.service;

import com.example.primesecur.model.Permission;
import com.example.primesecur.model.Tasks;
import com.example.primesecur.model.User;
import com.example.primesecur.repository.PermissionRepository;
import com.example.primesecur.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

public class UserService implements UserDetailsService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user != null){
            return user;
        }else{
            throw new UsernameNotFoundException("User Not found");
        }
    }

    @Transactional
    public User addUser(User user){
        ///////////////////////////////////////
        Permission userPermission = permissionRepository.findByRole("ROLE_USER");
        if (userPermission == null) {
            userPermission = new Permission("ROLE_USER");
            permissionRepository.save(userPermission);
        }
        user.setPermissions(Collections.singletonList(userPermission));
        /////////////////////////////////////


        User checkUser = userRepository.findByEmail(user.getEmail());
        if (checkUser == null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        return null;
    }

    @org.springframework.transaction.annotation.Transactional
    public User updatePassword(String newPassword, String oldPassword){
        User currentUser = getCurrentSessionUser();
        if (passwordEncoder.matches(oldPassword, currentUser.getPassword())){
            currentUser.setPassword(passwordEncoder.encode(newPassword));
            return userRepository.save(currentUser);
        }
        return null;
    }

    public User getCurrentSessionUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            User user = (User) authentication.getPrincipal();
            if (user != null){
                return user;
            }
        }
        return null;
    }

    @Transactional
    public boolean canUserAccessTask(User user, Long taskId) {
        for (Tasks task : user.getTasks()) {
            if (task.getId().equals(taskId)) {
                return true;
            }
        }
        return false;
    }



}
