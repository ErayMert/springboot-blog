package com.emert.blog.service;


import com.emert.blog.entity.Role;
import com.emert.blog.entity.User;
import com.emert.blog.exception.RTBusinessException;
import com.emert.blog.exception.ResourceNotFoundException;
import com.emert.blog.payload.request.LoginRequest;
import com.emert.blog.payload.request.RegisterRequest;
import com.emert.blog.repository.RoleRepository;
import com.emert.blog.repository.UserRepository;
import com.emert.blog.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public String login(LoginRequest request){
        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
    }

    public String register(RegisterRequest request){

        if(Boolean.TRUE.equals(userRepository.existsByEmailOrUsername(request.getEmail(), request.getUsername()))){
            throw new RTBusinessException("User is already exist");
        }

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new ResourceNotFoundException("User Role does not found"));
        roles.add(userRole);
        
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();

        userRepository.save(user);

        return "Register successfully";
    }
}
